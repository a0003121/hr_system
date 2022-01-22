package com.project.HR;

import com.project.HR.vo.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {
//    @Autowired
//    private WebApplicationContext wac;
//    private MockMvc mvc;
//    private MockHttpSession session;
//
//    @Before
//    public void setupMockMvc() {
//        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc對象
//        session = new MockHttpSession();
//        User user = new User();
//        user.setName("root");
//        user.setPassword("root");
//        session.setAttribute("user", user); //攔截器那邊會判斷用戶是否登錄，所以這裡注入一個用戶
//    }
//
//    /**
//     * 新增教程測試用例
//     *
//     * @throws Exception
//     */
//    @Test
//    public void addLearn() throws Exception {
//        String json = "{\"author\":\"HAHAHAA\",\"title\":\"Spring\",\"url\":\"http://tengj.top/\"}";
//        mvc.perform(MockMvcRequestBuilders.post("/learn/add")
//                        .accept(MediaType.APPLICATION_JSON_UTF8)
//                        .content(json.getBytes()) //傳json參數
//                        .session(session)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//    /**
//     * 獲取教程測試用例
//     *
//     * @throws Exception
//     */
//    @Test
//    public void qryLearn() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get("/learn/resource/1001")
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .accept(MediaType.APPLICATION_JSON_UTF8)
//                        .session(session)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("嘟嘟MD獨立博客"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Spring Boot乾貨系列"))
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//    /**
//     * 修改教程測試用例
//     *
//     * @throws Exception
//     */
//    @Test
//    public void updateLearn() throws Exception {
//        String json = "{\"author\":\"測試修改\",\"id\":1031,\"title\":\"Spring Boot乾貨系列\",\"url\":\"http://tengj.top/\"}";
//        mvc.perform(MockMvcRequestBuilders.post("/learn/update")
//                        .accept(MediaType.APPLICATION_JSON_UTF8)
//                        .content(json.getBytes())//傳json參數
//                        .session(session)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }
//
//    /**
//     * 刪除教程測試用例
//     *
//     * @throws Exception
//     */
//    @Test
//    public void deleteLearn() throws Exception {
//        String json = "[1031]";
//        mvc.perform(MockMvcRequestBuilders.post("/learn/delete")
//                        .accept(MediaType.APPLICATION_JSON_UTF8)
//                        .content(json.getBytes())//傳json參數
//                        .session(session)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }
}
