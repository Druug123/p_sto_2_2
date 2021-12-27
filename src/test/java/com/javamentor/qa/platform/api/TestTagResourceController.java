package com.javamentor.qa.platform.api;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractApiTest;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Rustam
 */

@DataSet(cleanBefore = true)
public class TestTagResourceController extends AbstractApiTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserService userService;

    @Test
    @DataSet(value = {
            "relatedTagsDto/tag.yml",
            "relatedTagsDto/answer.yml",
            "relatedTagsDto/question.yml",
            "relatedTagsDto/user.yml",
            "relatedTagsDto/role.yml",
            "relatedTagsDto/questionHasTag.yml"
    })
    public void getRelatedTagDto() throws Exception {

        this.mvc.perform(get("/api/user/tag/related"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].id", is(100)))
                .andExpect(jsonPath("$[0].countQuestion", is(10)))
                .andExpect(jsonPath("$[4].id", is(104)))
                .andExpect(jsonPath("$[4].countQuestion", is(6)))
                .andExpect(jsonPath("$[8].id", is(108)))
                .andExpect(jsonPath("$[8].countQuestion", is(2)));
    }

    @Test
    public void getEmptyListRelatedTagDto() throws Exception {

        this.mvc.perform(get("/api/user/tag/related"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

    }

    @Test
    @DataSet(value = {
            "datasets/tagDatasets/role.yml",
            "datasets/tagDatasets/user.yml",
            "datasets/tagDatasets/tag.yml",
            "datasets/tagDatasets/tagIgnore.yml"
    })
    void getAllIgnoredTagDto() throws Exception {
        this.mvc.perform(get("/api/user/tag/ignored"))
                .andExpect(status().isOk());
        Assertions.assertTrue(entityManager.createQuery("select t.ignoredTag.id from IgnoredTag t where t.user.id=:id", Long.class)
                .setParameter("id", 2L)
                .getResultList()
                .containsAll(Arrays.asList(100L, 101L)));
    }

    @Test
    @DataSet(value = {
            "datasets/tagDatasets/tagTrack.yml"
    })
    void getAllTrackedTagDto() throws Exception {
        this.mvc.perform(get("/api/user/tag/tracked"))
                .andExpect(status().isOk());
        Assertions.assertTrue(entityManager.createQuery("select t.trackedTag.id from TrackedTag t where t.user.id=:id", Long.class)
                .setParameter("id", 2L)
                .getResultList()
                .containsAll(Arrays.asList(102L, 103L)));
    }
}
