package scholar.kromfo.Classes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughActivity;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughCard;

import java.util.ArrayList;
import java.util.List;

import scholar.kromfo.R;

public class WalkThrough extends FancyWalkthroughActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FancyWalkthroughCard fancywalkthroughCard1 = new FancyWalkthroughCard("Find Your Phone", "You Can Track Your Stolen Phone Easily.",R.drawable.carouselslide);
        FancyWalkthroughCard fancywalkthroughCard2 = new FancyWalkthroughCard("Prevent Your Phone From Being Stolen", "Protect Your Phone From Being Stolen When Charging.",R.drawable.carouselslide);
        FancyWalkthroughCard fancywalkthroughCard3 = new FancyWalkthroughCard("Prevent Pick-Picketing", "Detect If Your Phone Is Taken From You.",R.drawable.carouselslide);


        fancywalkthroughCard1.setBackgroundColor(R.color.white);
        fancywalkthroughCard1.setIconLayoutParams(300,300,0,0,0,0);
        fancywalkthroughCard2.setBackgroundColor(R.color.white);
        fancywalkthroughCard2.setIconLayoutParams(300,300,0,0,0,0);
        fancywalkthroughCard3.setBackgroundColor(R.color.white);
        fancywalkthroughCard3.setIconLayoutParams(300,300,0,0,0,0);
        List<FancyWalkthroughCard> pages = new ArrayList<>();

        pages.add(fancywalkthroughCard1);
        pages.add(fancywalkthroughCard2);
        pages.add(fancywalkthroughCard3);


        for (FancyWalkthroughCard page : pages) {
            page.setTitleColor(R.color.black);
            page.setDescriptionColor(R.color.black);
        }
        setFinishButtonTitle("Get Started");
        showNavigationControls(true);
        setColorBackground(R.color.colorPrimaryDark);
        setImageBackground(R.drawable.carouselslide);
        setInactiveIndicatorColor(R.color.sc_red);
        setActiveIndicatorColor(R.color.colorAccent);
        setOnboardPages(pages);

    }

    @Override
    public void onFinishButtonPressed() {
        startActivity(new Intent(WalkThrough.this,Register.class));

    }
}