package com.project.HR;

import com.project.HR.dao.DeptDAO;
import com.project.HR.vo.Dept;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;


@RunWith( SpringRunner.class)
@SpringBootTest
class HrApplicationTests {
	@Autowired
	private DeptDAO deptDAO;


	@Test
	public void getLearn(){
		Dept dept=deptDAO.findById(1).get();
		MatcherAssert.assertThat(dept.getName(), is("行政部"));
	}

}
