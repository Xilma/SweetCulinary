package rilma.example.com.sweetculinary.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rilma.example.com.sweetculinary.R;
import rilma.example.com.sweetculinary.adapters.StepAdapter;
import rilma.example.com.sweetculinary.models.Step;
import rilma.example.com.sweetculinary.utils.ConstantValues;

public class TutorialActivity extends AppCompatActivity implements View.OnClickListener, StepAdapter.OnStepClick{

    public static final String STEP_LIST_STATE = "step_list_state";
    public static final String STEP_NUMBER_STATE = "step_number_state";
    public static final String STEP_LIST_JSON_STATE = "step_list_json_state";
    private boolean isTablet;
    private int videoNumber = 0;
    private StepAdapter stepAdapter;
    
    @BindView(R.id.bv_next_step)
    Button nextStep;

    @BindView(R.id.bv_previous_step)
    Button previousStep;
    
    ArrayList<Step> stepList = new ArrayList<>();
    String jsonResult;
    boolean isFromWidget;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        // Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        // Check if device is a tablet
        if(findViewById(R.id.cooking_tablet) != null){
            isTablet = true;
        }
        else{
            isTablet = false;
        }*/

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(ConstantValues.STEP_INTENT_EXTRA)) {
                stepList = getIntent().getParcelableArrayListExtra(ConstantValues.STEP_INTENT_EXTRA);
            }
            if (intent.hasExtra(ConstantValues.JSON_RESULT_EXTRA)) {
                jsonResult = getIntent().getStringExtra(ConstantValues.JSON_RESULT_EXTRA);
            }
            if(intent.getStringExtra(ConstantValues.WIDGET_EXTRA) != null){
                isFromWidget = true;
            }
            else{
                isFromWidget = false;
            }
        }
        // If there is no saved state, instantiate fragment
        if(savedInstanceState == null){
            playVideo(stepList.get(videoNumber));
        }

        ButterKnife.bind(this);

        handleUiForDevice();
    }

    // Initialize fragment
    public void playVideo(Step step){
        VideoFragment videoPlayerFragment = new VideoFragment();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelable(ConstantValues.STEP_SINGLE, step);
        videoPlayerFragment.setArguments(stepsBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fl_player_container, videoPlayerFragment)
                .addToBackStack(null)
                .commit();
    }

    // Initialize fragment
    public void playVideoReplace(Step step){
        VideoFragment videoPlayerFragment = new VideoFragment();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelable(ConstantValues.STEP_SINGLE, step);
        videoPlayerFragment.setArguments(stepsBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_player_container, videoPlayerFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_LIST_STATE, stepList);
        outState.putString(STEP_LIST_JSON_STATE, jsonResult);
        outState.putInt(STEP_NUMBER_STATE, videoNumber);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        //If it's last step show cooking is over
        if(videoNumber == stepList.size()-1){
            Toast.makeText(this, R.string.end_tutorial, Toast.LENGTH_SHORT).show();
        }
        else{
            if(v.getId() == previousStep.getId()){
                videoNumber--;
                if(videoNumber < 0){
                    Toast.makeText(this, R.string.next_step, Toast.LENGTH_SHORT).show();
                }
                else
                    playVideoReplace(stepList.get(videoNumber));
            }
            else if(v.getId() == nextStep.getId()){
                videoNumber++;
                playVideoReplace(stepList.get(videoNumber));
            }
        }
    }

    @Override
    public void onStepClick(int position) {
        videoNumber = position;
        playVideoReplace(stepList.get(position));
    }

    public void handleUiForDevice(){
        if(!isTablet){
            // Set button listeners
            nextStep.setOnClickListener(this);
            previousStep.setOnClickListener(this);
        }

        else{//Tablet view
            /*stepAdapter = new StepAdapter(this,stepList, this, videoNumber);
            linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerViewSteps.setLayoutManager(linearLayoutManager);
            mRecyclerViewSteps.setAdapter(stepAdapter);*/
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            stepList = savedInstanceState.getParcelableArrayList(STEP_LIST_STATE);
            jsonResult = savedInstanceState.getString(STEP_LIST_JSON_STATE);
            videoNumber = savedInstanceState.getInt(STEP_NUMBER_STATE);
        }
    }

}
