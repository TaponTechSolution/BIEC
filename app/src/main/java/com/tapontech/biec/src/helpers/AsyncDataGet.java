package com.tapontech.biec.src.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.tapontech.biec.src.utils.BIECException;
import com.tapontech.biec.src.utils.ErrorInfo;
import com.tapontech.biec.src.utils.ErrorInfoFactory;

import java.util.List;


/**
 * Created by sanjay on 12-02-2016.
 */
public class AsyncDataGet extends AsyncTask<String, Void, BIECException> {

    public static ProgressDialog progressDialog = null;
    private Context context = null;
    private GetDataInterface getDataObj = null;
    private boolean executionSuccess = false;
    private BIECException exceptions = null;
    private String prompt = "";

    public AsyncDataGet(Context _context, GetDataInterface _getDataObj, String _prompt){
        context = _context;
        getDataObj = _getDataObj;
        prompt = _prompt;
    }

    public BIECException getExceptions() {
        return exceptions;
    }

    public boolean isExecutionSuccess() {
        return this.executionSuccess;
    }

    private void setExecutionSuccess(boolean _executionSuccess) {
        this.executionSuccess = _executionSuccess;
    }

    // can use UI thread here
    protected void onPreExecute() {
        // display progress dialog while data is being loaded
        progressDialog = ProgressDialog.show(context, "Please wait...", prompt, true);
        progressDialog.getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    protected BIECException doInBackground(String... params) {

        try{
            boolean status = getDataObj.execute();
            setExecutionSuccess(status);
            progressDialog.dismiss();
        }
        catch(BIECException ex){
            setExecutionSuccess(false);
            exceptions = ex;
            if(exceptions != null){
                Log.e("AsyncDataGet::doInBackground", "Exception encountered. Cancelling execution");
                List<ErrorInfo> errorInfoList = exceptions.getErrorInfoList();
                for(ErrorInfo errInfo : errorInfoList){
                    Log.e("AsyncDataGet::doInBackground", errInfo.getErrorDescription());
                }
            }
            cancel(true);
        }
        catch(Exception ex){
            //Toast.makeText(context, "Internet connection unavailable", Toast.LENGTH_SHORT).show();
            setExecutionSuccess(false);
            BIECException _ex = new BIECException();
            ErrorInfo errInfo = ErrorInfoFactory.getGenericErrorInfo(ex, "AsyncDataGet");
            _ex.addInfo(errInfo);

            Log.e("AsyncDataGet::doInBackground", "Exception encountered. Cancelling execution");
            cancel(true);
        }
        finally{
            progressDialog.dismiss();
        }

        return exceptions;
    }

    // can use UI thread here
    protected void onPostExecute(BIECException ex) {
        Log.d("AsyncDataGet::onPostExecute", "entering...");
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(isCancelled() && exceptions != null){
            progressDialog.dismiss();
            List<ErrorInfo> errorInfoList = exceptions.getErrorInfoList();
            for(ErrorInfo errInfo : errorInfoList){
                Log.e("AsyncDataGet::onPostExecute", errInfo.getErrorDescription());
                Toast t = Toast.makeText(context , errInfo.getErrorDescription(), Toast.LENGTH_LONG);
                t.show();
            }
        }
        else {
            Log.d("AsyncDataGet::onPostExecute", "Error info list is Null");
            progressDialog.dismiss();
            getDataObj.postExecute(executionSuccess);
        }
    }
}
