<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="percentual" required="true"%>
<%@ attribute name="nome" required="true"%>
<%@ attribute name="url" required="true"%>

<c:if test="${percentual eq 0}">
    <c:set var="corPercentual" value="#DC3912" />
</c:if>
<c:if test="${percentual lt 50 and percentual != 0}">
    <c:set var="corPercentual" value="#FF9900" />
</c:if>
<c:if test="${percentual gt 50 and percentual != 100}">
    <c:set var="corPercentual" value="#3366CC" />
</c:if>
<c:if test="${percentual eq 100}">
    <c:set var="corPercentual" value="#109618" />
</c:if>
<a href="${url}" style="color: ${corPercentual}">${nome}</a>