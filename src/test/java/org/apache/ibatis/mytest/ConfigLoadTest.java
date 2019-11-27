package org.apache.ibatis.mytest;

import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Description: <br>
 *
 * @author 作者 <a href='shuai.deng@moji.com'>shuai.deng</a>
 * @version 创建时间：2019-08-05.
 */
public class ConfigLoadTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeAll
  static void setUp() throws Exception {
    // create a SqlSessionFactory
    try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/mytest/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,"development");
    }

    // populate in-memory database
    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
      "org/apache/ibatis/mytest/CreateDB.sql");
  }

  @Test
  void fullyPopulatedSubject() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
      final Object subject = mapper.getSubject(1);
      assertNotNull(subject);
    }
  }
}
