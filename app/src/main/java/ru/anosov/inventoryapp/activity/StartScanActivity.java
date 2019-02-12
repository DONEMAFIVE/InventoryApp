package ru.anosov.inventoryapp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;

import ru.anosov.inventoryapp.ItemListObjectAdapter;
import ru.anosov.inventoryapp.ItemListViewObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import ru.anosov.inventoryapp.R;
import static ru.anosov.inventoryapp.activity.StartInventActivity.MOL;

public class StartScanActivity extends AppCompatActivity implements ItemListObjectAdapter.onItemClickListener {
    public static final String NAME = "name";
    public static final String NUMBER = "number";
    public static final String STATUS = "status";

    private RecyclerView mRecycleView;
    private ItemListObjectAdapter mAdapter;
    private ArrayList<ItemListViewObject> mItemList;
    private RequestQueue mRequestQ;
    private FloatingActionButton fabComplite, fabNotFound, fabScaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_scan);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("ИНВЕНТАРИЗАЦИЯ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String mol = intent.getStringExtra(MOL);

        mRecycleView = findViewById(R.id.recycler_view_obj);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mItemList = new ArrayList<>();

        fabComplite = findViewById(R.id.complite);
        fabNotFound = findViewById(R.id.not_found);
        fabScaning = findViewById(R.id.scaning);

        fabComplite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast complite = Toast.makeText(getApplicationContext(), "Complite...", Toast.LENGTH_SHORT);
                complite.show();
            }
        });

        fabNotFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast nf = Toast.makeText(getApplicationContext(), "Not Found...", Toast.LENGTH_SHORT);
                nf.show();
            }
        });

        fabScaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast scaning = Toast.makeText(getApplicationContext(), "Scaning...", Toast.LENGTH_SHORT);
                scaning.show();
            }
        });

        mRequestQ = Volley.newRequestQueue(this);
        parseJSON(mol);
    }

    String IP = MainActivity.cfg.getIP();

    private void parseJSON(String mol) {

        String url = "http://" + IP + ":2298/dataformol?mol=" + mol + "";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Inventory");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String number = jsonObject.getString("number");
                        String status = jsonObject.getString("status");

                        mItemList.add(new ItemListViewObject(name, number, status));
                    }

                    mAdapter = new ItemListObjectAdapter(StartScanActivity.this, mItemList);
                    mRecycleView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(StartScanActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQ.add(request);
    }
    @Override
    public void onItemClick(int position) {

        ItemListViewObject clicedItem = mItemList.get(position);
        Intent scanInvent = new Intent(this, EditActivity.class);

        scanInvent.putExtra(NAME, clicedItem.getmName());
        scanInvent.putExtra(NUMBER, clicedItem.getmNumber());
        scanInvent.putExtra(STATUS, clicedItem.getmStatus());

        startActivity(scanInvent);
    }
}
