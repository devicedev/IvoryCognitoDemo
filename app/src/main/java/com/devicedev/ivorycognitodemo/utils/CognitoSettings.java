package com.devicedev.ivorycognitodemo.utils;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {

//    public static final String USER_POOL_ID = "us-east-1_np6puveP7";
    public static final String USER_POOL_ID = "us-east-1_xc3x7dWs8";

//    public static final String CLIENT_ID = "7elr1kv5s9ue95kfj3o3n6hg8h";
    public static final String CLIENT_ID = "6quem4jlgmoks42474vnlrdotg";

//    public static final String CLIENT_SECRET = "1fv6va69f0j0bu8pe4cp3o9lf5hm6qt1jthochvncbk03a8qpj6j";
    public static final String CLIENT_SECRET = "1j18ajrh3i2bbkskajtrpodls1pnbolgvovcnjhchh03gqbopcmk";

    public static final Regions REGION = Regions.US_EAST_1;

    private Context context;

    public CognitoSettings(Context context) {
        this.context = context;
    }

    public CognitoUserPool getUserPool() {
        return new CognitoUserPool(this.context, USER_POOL_ID, CLIENT_ID, CLIENT_SECRET, REGION);
    }
}
