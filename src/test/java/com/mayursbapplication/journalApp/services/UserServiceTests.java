package com.mayursbapplication.journalApp.services;

import com.mayursbapplication.journalApp.entity.User;
import com.mayursbapplication.journalApp.repository.UserRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @BeforeAll
    public static void testStarts (){
        System.out.println("Test Starts");
    }

    @AfterAll
    public static void testComplete (){
        System.out.println("Test Completed!!!");
    }



 //   @Disabled  // if you don't want to run the particular test when we run the class then make it disabled.
    @Test
    public void findByUserName(){

        User user = userRepo.findByUserName("Ronaldo");
        assertNotNull(user);
        assertTrue(!user.getJournalEntries().isEmpty());


    }

    @ParameterizedTest
    @ValueSource(strings ={
               "Ronaldo",
                "Messi",
                "Neymar"
            })
    public void findByUserName1(String name){

        assertNotNull( userRepo.findByUserName(name),"Failed for "+name);

    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "10,22,32",
            "5,0,6",
            "2,8,10"
    })
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void testSaveNewUser(User user){

        assertTrue(userService.saveNewUser(user));

    }


}
