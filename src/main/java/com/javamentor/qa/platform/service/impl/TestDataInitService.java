package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.*;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TestDataInitService {

    private final RoleService roleService;
    private final UserService userService;
    private final Flyway flyway;
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final TagService tagService;

    @Autowired
    public TestDataInitService(RoleService roleService, UserService userService, Flyway flyway, AnswerService answerService, QuestionService questionService, TagService tagService) {
        this.roleService = roleService;
        this.userService = userService;
        this.flyway = flyway;
        this.answerService = answerService;
        this.questionService = questionService;
        this.tagService = tagService;
    }

    public void init() {
        flyway.clean();
        flyway.migrate();
        addRole();
        addUser();
        addTag();
        addQuestion();
        addAnswer();
    }

    private void addRole() {
        // изменить при необходимости
        roleService.persist(new Role("ADMIN"));
        roleService.persist(new Role("USER"));
    }

    private void addTag() {
        StringBuilder name = new StringBuilder();
        StringBuilder description = new StringBuilder();

        for (int x = 1; x <= 50; x++) {
            name.delete(0, name.length()).append("tag:").append(x);
            description.delete(0, description.length()).append("this is tag by name: ").append(name);

            Tag tag = new Tag();
            tag.setName(name.toString());
            tag.setDescription(description.toString());
            tagService.persist(tag);
            name.delete(0, name.length());
            description.delete(0, description.length());
        }
    }

    private void addQuestion() {
        StringBuilder title = new StringBuilder();
        StringBuilder description = new StringBuilder();
        List<Tag> tags = new ArrayList<>();

        for (int x = 1; x <= 50; x++) {
            tags.clear();
            title.delete(0, 50).append("title:").append(x);
            description.delete(0, 50).append("this is question by title: ").append(title);

            // добавление рандомных тегов в рандомном количестве [1;5]
            for (int y = (1 + (int) (Math.random() * 4)); y <= 5; y++) {
                Tag tag = tagService.getById((long) (1 + (int) (Math.random() * 49))).get();
                if (!tags.contains(tag)) {
                    tags.add(tag);
                }
            }

            Question question = new Question();
            question.setTitle(title.toString());
            question.setDescription(description.toString());
            question.setTags(tags);
            // добавление рандомного юзера
            question.setUser(userService.getById((long) (1 + (int) (Math.random() * 49))).get());
            questionService.persist(question);
        }
    }

    private void addAnswer() {
        StringBuilder htmlBody = new StringBuilder();
        Random random = new Random();
        // добавление радномного количества ответов [1;50]
        for (int x = 1 + (int) (Math.random() * 49); x <= 50; x++) {
            htmlBody.delete(0, htmlBody.length()).append("htmlBody:").append(x);
            Answer answer = new Answer();
            answer.setIsDeleted(random.nextBoolean());
            answer.setHtmlBody(htmlBody.toString());
            answer.setIsHelpful(random.nextBoolean());
            answer.setIsDeletedByModerator(random.nextBoolean());
            answer.setQuestion(questionService.getById((long) (1 + (int) (Math.random() * 49))).get());
            answer.setUser(userService.getById((long) (1 + (int) (Math.random() * 49))).get());
            answerService.persist(answer);
        }
    }

    private void addUser() {
        // изменить при необходимости
        Role adminRole = roleService.getById(1L).get();
        Role userRole = roleService.getById(2L).get();

        StringBuilder email = new StringBuilder();
        StringBuilder password = new StringBuilder();
        StringBuilder fullName = new StringBuilder();
        StringBuilder city = new StringBuilder();
        StringBuilder linkSite = new StringBuilder();
        StringBuilder linkGitHub = new StringBuilder();
        StringBuilder linkVk = new StringBuilder();
        StringBuilder about = new StringBuilder();
        StringBuilder imageLink = new StringBuilder();
        StringBuilder nickname = new StringBuilder();

        User admin = new User();
        admin.setRole(adminRole);
        admin.setEmail(email.append("Vasya@mail.ru").toString());
        admin.setPassword(password.append("password").toString());
        admin.setFullName(fullName.append("Vasya").toString());
        admin.setCity(city.append("Vasya's City").toString());
        admin.setLinkSite(linkSite.append("vasya.ru").toString());
        admin.setLinkGitHub(linkGitHub.append("github.com/vasya").toString());
        admin.setLinkVk(linkVk.append("vk.com/vasya").toString());
        admin.setAbout(about.append("Hello my name is Vasya").toString());
        admin.setImageLink(imageLink.append("vasya.ru/myphoto/1").toString());
        admin.setNickname(nickname.append("Vasya1337").toString());
        userService.persist(admin);

        User admin1 = new User();
        admin1.setRole(adminRole);
        admin1.setEmail(email.delete(0, email.length()).append("123@mail.ru").toString());
        admin1.setPassword(password.delete(0, password.length()).append("123").toString());
        admin1.setFullName(fullName.delete(0, fullName.length()).append("Volodya").toString());
        admin1.setCity(city.delete(0, city.length()).append("Volodya's City").toString());
        admin1.setLinkSite(linkSite.delete(0, linkSite.length()).append("Volodya.ru").toString());
        admin1.setLinkGitHub(linkGitHub.delete(0, linkGitHub.length()).append("github.com/Volodya").toString());
        admin1.setLinkVk(linkVk.delete(0, linkVk.length()).append("github.com/Volodya").toString());
        admin1.setAbout(about.delete(0, about.length()).append("Hello my name is Volodya").toString());
        admin1.setImageLink(imageLink.delete(0, imageLink.length()).append("Volodya.ru/myphoto/1").toString());
        admin1.setNickname(nickname.delete(0, nickname.length()).append("Vova").toString());
        userService.persist(admin1);

        for (int x = 2; x <= 50; x++) {

            email.delete(0, email.length()).append(x).append("user@mail.ru");
            password.delete(0, password.length()).append(x).append(111);
            fullName.delete(0, fullName.length()).append(x).append("user");
            city.delete(0, city.length()).append(x).append("user's City");
            linkSite.delete(0, linkSite.length()).append(x).append("user.ru");
            linkGitHub.delete(0, linkGitHub.length()).append("github.com/").append(x).append("user");
            linkVk.delete(0, linkVk.length()).append("vk.com/").append(x).append("user");
            about.delete(0, about.length()).append("Hello my name is ").append(x).append("user");
            imageLink.delete(0, imageLink.length()).append(x).append("user").append(".ru/myphoto/1");
            nickname.delete(0, nickname.length()).append(x).append("user");

            User u = new User();
            u.setRole(userRole);
            u.setEmail(email.toString());
            u.setPassword(password.toString());
            u.setFullName(fullName.toString());
            u.setCity(city.toString());
            u.setLinkSite(linkSite.toString());
            u.setLinkGitHub(linkGitHub.toString());
            u.setLinkVk(linkVk.toString());
            u.setAbout(about.toString());
            u.setImageLink(imageLink.toString());
            u.setNickname(nickname.toString());

            userService.persist(u);
        }


    }
}
