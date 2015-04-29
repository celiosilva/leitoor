package br.com.delogic.leitoor.util.csa;

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;

import br.com.delogic.csa.manager.ContentManager;
import br.com.delogic.jfunk.Has;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Caching policy from
 * https://developers.google.com/speed/docs/best-practices/caching
 *
 * @author celio@delogic.com.br
 *
 */
public class S3ContentManager implements ContentManager {

    private String                     bucket;
    private boolean                    cache   = true;
    private String                     endpoint;
    private String                     path;
    private AmazonS3                   client;
    private Iterator<? extends Object> iterator;
    private MimetypesFileTypeMap       typeMap = new MimetypesFileTypeMap();

    private AWSCredentials             credentials;

    @PostConstruct
    public void init() {
        if (!Has.content(endpoint) || !endpoint.startsWith("http")) {
            throw new IllegalArgumentException("Endpoint must be a full URL containing the protocol starting by http");
        }
        client = new AmazonS3Client(credentials);
        client.setEndpoint(endpoint);
        path = endpoint + "/" + bucket + "/";
    }

    @Override
    public String get(String name) {
        return path + name;
    }

    @Override
    public String create(InputStream inputStream, String fileName) {
        fileName = getRealFileName(fileName);
        String val = iterator.next().toString();
        String newFileName = "file" + val + "." + getFileExtension(fileName);

        ObjectMetadata metadata = createMetadata(newFileName);
        PutObjectRequest req = new PutObjectRequest(bucket, newFileName, inputStream, metadata);
        req.setCannedAcl(CannedAccessControlList.PublicRead);

        client.putObject(req);

        return newFileName;
    }

    @Override
    public void update(InputStream inputStream, String fileName) {
        fileName = getRealFileName(fileName);

        ObjectMetadata metadata = createMetadata(fileName);

        PutObjectRequest req = new PutObjectRequest(bucket, fileName, inputStream, metadata);
        req.setCannedAcl(CannedAccessControlList.PublicRead);
        client.putObject(req);
    }

    private ObjectMetadata createMetadata(String fileName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(typeMap.getContentType(fileName));
        if (cache) {
            metadata.setHttpExpiresDate(todayPlus(365)); // 1year
            metadata.setLastModified(new Date());
            metadata.setCacheControl("public");
        }
        return metadata;
    }

    private Date todayPlus(int dias) {
        return new Date(System.currentTimeMillis() + (dias * 24 * 60 * 60 * 1000));
    }

    private String getRealFileName(String fileName) {
        return fileName.replace(path, "");
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".") || fileName.endsWith(".")) {
            throw new IllegalArgumentException("File name doesn't have any extension");
        }
        return fileName.substring(fileName.indexOf(".") + 1);
    }

    public AWSCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(AWSCredentials credentials) {
        this.credentials = credentials;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public Iterator<? extends Object> getIterator() {
        return iterator;
    }

    public void setIterator(Iterator<? extends Object> iterator) {
        this.iterator = iterator;
    }

    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Defines the endpoint for this operation depending on the region used.
     * It's required the endpoint must be a full URL containing the protocol and
     * the endpoint itself.
     *
     * @param endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    @Override
    public InputStream getInpuStream(String name) throws Exception {
        return client.getObject(new GetObjectRequest(bucket, name)).getObjectContent();
    }

}
