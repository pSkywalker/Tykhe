package com.app.tykhe.onBoarding;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.app.tykhe.OnBoardingActivity;
import com.app.tykhe.R;
import com.app.tykhe.viewModels.UserOnBoardingViewModel;

public class WelcomeFragment extends Fragment {

    private UserOnBoardingViewModel onBoardingForm;

    private TextView termsOfServiceTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View view = (ViewGroup) inflater.inflate(
        //        R.layout.fragment_welcome_screen, container, false);

        ViewDataBinding bindingHandle = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome_screen, container, false);
        bindingHandle.setVariable(1, ((OnBoardingActivity) getActivity()).getOnBoardingDataSource());

        getActivity().getWindow().setStatusBarColor(getResources().getColor( R.color.black ));

        return bindingHandle.getRoot();

    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ){
        //this.onBoardingForm = ;

        this.termsOfServiceTextView = (TextView) getView().findViewById(R.id.termsOfService);
        this.termsOfServiceTextView.setText(getResources().getString(R.string.welcome_termsandservice), TextView.BufferType.SPANNABLE);
        this.termsOfServiceTextView.setMovementMethod(LinkMovementMethod.getInstance());

        ((SpannableString)this.termsOfServiceTextView.getText()).setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Log.d( "from spannable", "spanned" );
                Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor( Color.WHITE );
            }
        }, 15, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((SpannableString)this.termsOfServiceTextView.getText()).setSpan(new UnderlineSpan(), 15, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //this.termsOfServiceTextView.setText( new SpannableString(this.termsOfServiceTextView.getText()).setSpan( termsOfServiceClickableSpan, 0, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE  ). );

    }



}
