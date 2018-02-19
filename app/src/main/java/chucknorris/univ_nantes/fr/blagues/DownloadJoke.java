package chucknorris.univ_nantes.fr.blagues;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by E173246L on 08/12/17.
 */

public class DownloadJoke extends AsyncTask<String, Void, ArrayList<Joke>> {

    private static final String BASE_URL = "http://api.icndb.com/jokes/random/60";
    private HttpURLConnection httpClient;
    private ProgressDialog progress;
    private JokeMasterActivity screen;

    public DownloadJoke(JokeMasterActivity s) {
        this.screen = s;
        this.progress = new ProgressDialog(this.screen);
    }

    @Override
    protected void onPreExecute() {
        progress.setTitle("Please wait");
        progress.setMessage("Fetching remote data...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    @Override
    protected ArrayList<Joke> doInBackground(String... params) {
        ArrayList<Joke> fetchData = new ArrayList<Joke>();
        String stream = null;

        try {
            URL url = new URL(BASE_URL);
            this.httpClient = (HttpURLConnection) url.openConnection();
            this.httpClient.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(this.httpClient.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            stream = sb.toString();

            JSONArray concepts = new JSONObject(stream).getJSONArray("value");
            for (int i = 0; i < concepts.length(); ++i) {
                JSONObject record = concepts.getJSONObject(i);
                fetchData.add(new Joke(record.getString("joke")));

            }
        } catch (Exception e) {
            Log.e("genDDROID", "An error occured while fetching", e);
        } finally {
            this.httpClient.disconnect();
        }
        return fetchData;
    }

    @Override
    protected void onPostExecute(ArrayList<Joke> result) {
        if (progress.isShowing()) {
            progress.dismiss();
        }
        this.screen.populate(result);
    }
}
