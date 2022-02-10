package com.project.HR;

import com.project.HR.dao.CalendarDAO;
import com.project.HR.dao.EmployeeDAO;
import com.project.HR.vo.Calendar;
import com.project.HR.vo.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//https://stackoverflow.com/questions/41315386/spring-boot-1-4-datajpatest-error-creating-bean-with-name-datasource
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataJpaTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeDAO employeeRepository;

    @Test
    public void Employee() {
        // given
        Employee alex = new Employee();
        alex.setEmpNo(123);
        alex.setName("alex");
        entityManager.persistAndFlush(alex);
        // when
        List<Employee> found = employeeRepository.findByEmpNo(alex.getEmpNo());
        // then
        assertThat(found.get(0).getName())
                .isEqualTo(alex.getName());
    }

    @Autowired
    CalendarDAO calendarDAO;
    @Test
    public void Calender() {
        // given
        Date date = new Date(new java.util.Date().getTime());
        Calendar calendar = new Calendar(0, date, 2, "note");
        entityManager.persistAndFlush(calendar);
        // when
        Calendar found = calendarDAO.findByDate(date);
        // then
        assertThat(found.getNote())
                .isEqualTo(calendar.getNote());
    }

//    @Autowired
//    private DeptDAO deptDAO;
//    @Test
//    public void getLearn(){
//        Dept dept=deptDAO.findById(1).get();
//        MatcherAssert.assertThat(dept.getName(), is("行政部"));
//    }
}
