package rilma.example.com.sweetculinary;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rilma.example.com.sweetculinary.views.DetailsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsDisplayTest {
    @Rule
    public IntentsTestRule<DetailsActivity> mActivityTestRule
            = new IntentsTestRule<>(DetailsActivity.class);

    @Test
    public void clickBeginButton_opensTutorialActivity(){
        //Find the view
        //Perform action on the view
        onView((withId(R.id.bv_begin))).perform(click());

        //Check if the view does what you expected

    }
}
