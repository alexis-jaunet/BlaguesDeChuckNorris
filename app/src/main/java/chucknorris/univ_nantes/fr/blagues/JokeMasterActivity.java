package chucknorris.univ_nantes.fr.blagues;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class JokeMasterActivity extends ListActivity {

    private JokeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.adapter = new JokeAdapter(this, R.layout.joke_item);
        this.setListAdapter(adapter);
    }

    protected void onStart () {
        super.onStart();
        new DownloadJoke(this).execute();
    }

    public void populate (ArrayList<Joke> data) {
        this.adapter.clear();
        this.adapter.addAll(data);
        this.adapter.notifyDataSetChanged();
    }
}
