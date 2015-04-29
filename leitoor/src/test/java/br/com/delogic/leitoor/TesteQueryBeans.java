package br.com.delogic.leitoor;

import org.junit.Test;

import br.com.delogic.csa.repository.sql.SqlQuery;
import br.com.delogic.csa.repository.sql.SqlQueryTester;

public class TesteQueryBeans extends SpringTeste {

    @Test
    public void testarTodasQueries() {
        SqlQueryTester tester = new SqlQueryTester();
        tester.testAllQueries(ctx.getBeansOfType(SqlQuery.class));
    }

}
