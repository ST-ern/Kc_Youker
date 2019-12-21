package com.yechaoa.materialdesign.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yechaoa.materialdesign.R;

import java.io.IOException;
import java.util.Collections;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FragmentTranslate extends Fragment {

    //okhttp
    private static String subscriptionKey = "a872995b97074a298f4adb299af2f121";
    private static String endpoint = "https://api.cognitive.microsofttranslator.com";
    String url = endpoint + "/translate?api-version=3.0&to=en";

    OkHttpClient client = new OkHttpClient.Builder()
            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
            .build();


    private EditText et_translate;
    private TextView tv_translate_result;
    private String translate_result;
    private Button btn_text_translate, btn_copy_result;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_translate, null);

        return view;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //okhttp
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        et_translate=(EditText)view.findViewById(R.id.translateBlock);
        tv_translate_result = (TextView)view.findViewById(R.id.tv_translate_result);
        btn_text_translate=(Button)view.findViewById(R.id.btn_text_translate);
        btn_copy_result = (Button)view.findViewById(R.id.btn_copy_result);

        btn_text_translate.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                String raw = et_translate.getText().toString().trim();
                Toast.makeText(getActivity(), raw, Toast.LENGTH_LONG).show();

                //返回的翻译文本魏Response，是一个经过prettify函数转化为字符串的翻译结果
                try {
                    String response = Post(raw);
                    translate_result = prettify(response);

                    tv_translate_result.setText(translate_result);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        btn_copy_result.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                copyText(translate_result);
            }
        });

    }

    public String Post(String text) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "[{\n\t\"Text\": \""+text+"\"\n}]");
        Request request = new Request.Builder()
                .url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                .addHeader("Content-type", "application/json").build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private void copyText(String copiedText) {
        ClipboardManager clipboardManager = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, copiedText));

//        getActivity().showToast("Copy Text: " + copiedText);
    }

    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String Response=gson.toJson(json);
        JsonArray jsonArray=parser.parse(Response).getAsJsonArray();

        JsonObject jsonObject=jsonArray.get(0).getAsJsonObject();
        JsonArray  translation=jsonObject.get("translations").getAsJsonArray();

        String txt = translation.get(0).getAsJsonObject().get("text").getAsString();
        return txt;
    }

}
