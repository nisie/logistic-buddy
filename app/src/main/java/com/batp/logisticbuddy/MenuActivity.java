package com.batp.logisticbuddy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.batp.logisticbuddy.client.CreateOrderActivity;
import com.batp.logisticbuddy.client.ListOrderActivity;
import com.batp.logisticbuddy.driverapplication.DriverMapsActivity;
import com.batp.logisticbuddy.client.OrderDetailActivity;
import com.batp.logisticbuddy.helper.SessionHandler;
import com.batp.logisticbuddy.server.ServerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.btn_start_driving)
    TextView startDriving;

    @BindView(R.id.list_order_button)
    TextView listOrder;

    @BindView(R.id.btn_create_order)
    TextView createOrder;

    @BindView(R.id.logout)
    TextView logout;

    @BindView(R.id.admin)
    TextView admin;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        initializeFirebase();
        initializeMenu(this);

    }

    private void initializeFirebase() {

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            initView();
        }

    }

    private void initView() {
//        startDriving.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MenuActivity.this, DriverActivity.class));
//            }
//        });
        createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, CreateOrderActivity.class));
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ServerActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.signOut();
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                finish();
            }
        });
        listOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, ListOrderActivity.class));
            }
        });
        startDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, DriverMapsActivity.class));
            }
        });
//        admin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MenuActivity.this, MapActivity.class));
//            }
//        });
    }

    private void initializeMenu(Context context) {
        switch (SessionHandler.getSession(context)){
            case SessionHandler.DRIVER:
                startDriving.setVisibility(View.VISIBLE);
                createOrder.setVisibility(View.GONE);
                admin.setVisibility(View.GONE);
                break;
            case SessionHandler.CLIENT:
                startDriving.setVisibility(View.GONE);
                createOrder.setVisibility(View.VISIBLE);
                admin.setVisibility(View.GONE);
                break;
            case SessionHandler.SERVER:
                startDriving.setVisibility(View.GONE);
                createOrder.setVisibility(View.GONE);
                admin.setVisibility(View.VISIBLE);
                break;
            default:
                startDriving.setVisibility(View.VISIBLE);
                createOrder.setVisibility(View.VISIBLE);
                admin.setVisibility(View.VISIBLE);
                break;
        }
    }
}
