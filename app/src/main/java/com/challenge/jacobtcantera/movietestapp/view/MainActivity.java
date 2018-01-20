package com.challenge.jacobtcantera.movietestapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.challenge.jacobtcantera.movietestapp.App;
import com.challenge.jacobtcantera.movietestapp.R;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplication()).getRestComponent().inject(this);
        presenter.initView(this);
        presenter.doCall();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
    }
}
