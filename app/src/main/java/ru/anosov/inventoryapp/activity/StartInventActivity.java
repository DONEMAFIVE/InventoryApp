package ru.anosov.inventoryapp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.melnykov.fab.FloatingActionButton;
import ru.anosov.inventoryapp.ItemListAdapter;
import ru.anosov.inventoryapp.ItemListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import ru.anosov.inventoryapp.R;

public class StartInventActivity extends AppCompatActivity implements ItemListAdapter.onItemClickListener {
    public static final String MOL = "mol";
    private RecyclerView mRecycleView;
    private ItemListAdapter mAdapter;
    private ArrayList<ItemListView> mItemList;
    private RequestQueue mRequestQ;

    String IP = MainActivity.cfg.getIP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_invent);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("ИНВЕНТАРИЗАЦИЯ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecycleView = findViewById(R.id.recycler_view);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mItemList = new ArrayList<>();
        mRequestQ = Volley.newRequestQueue(this);
        parseJSON();

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecycleView);
    }

    private void parseJSON(){
        String url = "http://"+IP+":2298/getmol";

        //Toast workComplete = Toast.makeText(getApplicationContext(), "get IP: "+IP, Toast.LENGTH_SHORT);
        //workComplete.show();

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Inventory");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String mol = jsonObject.getString("mol");
                        mItemList.add(new ItemListView(mol));
                    }

                    mAdapter = new ItemListAdapter(StartInventActivity.this, mItemList);
                    mRecycleView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(StartInventActivity.this);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_toolbar, menu);

        /*MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });*/
        return true;
    }

    @Override
    public void onItemClick(int position) {
        Intent scanInvent = new Intent(this, StartScanActivity.class);
        ItemListView clicedItem = mItemList.get(position);

        scanInvent.putExtra(MOL, clicedItem.getmMol());
        startActivity(scanInvent);
    }

}
