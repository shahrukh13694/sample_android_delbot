package com.example.delbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    RecyclerView recyclerView;
    ChatAdapter chatAdapter;
    List<ChatType> chatTypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = findViewById(R.id.inputText);
        recyclerView = findViewById(R.id.chat);
        chatTypeList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatTypeList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(chatAdapter);

        inputText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    inputText.setHint("");
                else
                    inputText.setHint("Ask Something");
            }
        });

        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    ChatType responseMessage = new ChatType(inputText.getText().toString(), true);
                    chatTypeList.add(responseMessage);
                    ChatType responseMessage2 = new ChatType(inputText.getText().toString(), false);
                    chatTypeList.add(responseMessage2);
                    inputText.setText("");
                    chatAdapter.notifyDataSetChanged();
                    if (!isLastVisible())
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                }
                return false;
            }
        });
    }
    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recyclerView.getAdapter().getItemCount();
        return (pos >= numItems);
    }
}
