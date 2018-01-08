package pl.pjwstk.pgmd.hearthlounge.authentication;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Maciek Dembowski on 08.01.2018.
 */
class SignUpTest {

//    @Test
//    void onStart() {
//    }
//
//    @Test
//    void onStop() {
//    }
//
//    @Test
//    void create_user() {
//    }
//
//    @Test
//    void add_new_user() {
//    }
//
//    @Test
//    void go_to_log_in() {
//    }
//
//    @Test
//    void check_logged_in() {
//    }
//
//    @Test
//    void updateUI() {
//    }

    SignUp signUp = new SignUp();
    @Test
    void isValidEmail() throws Exception {

        assertThat(signUp.isValidEmail("test@test.com"), is(true));

    }

    @Test
    void isValidPassword() {
    }

    @Test
    void isValidConfirmPassword() {
    }

}