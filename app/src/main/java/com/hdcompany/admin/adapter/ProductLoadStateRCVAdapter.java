package com.hdcompany.admin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.hdcompany.admin.R;
import com.hdcompany.admin.databinding.LoadStateItemRcvBinding;

public class ProductLoadStateRCVAdapter extends LoadStateAdapter<ProductLoadStateRCVAdapter.ProductLoadStateViewHolder> {

    private View.OnClickListener mRetryCallback;
    public ProductLoadStateRCVAdapter(View.OnClickListener mRetryCallback){
        this.mRetryCallback = mRetryCallback
    ;}
    @Override
    public void onBindViewHolder(@NonNull ProductLoadStateViewHolder productLoadStateViewHolder, @NonNull LoadState loadState) {
        productLoadStateViewHolder.bind(loadState);
    }
    @NonNull
    @Override
    public ProductLoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull LoadState loadState) {
        return new ProductLoadStateViewHolder(viewGroup,mRetryCallback);
    }

    public class ProductLoadStateViewHolder extends RecyclerView.ViewHolder{
            private ProgressBar mProgressBar;
            private TextView mErrorMsg;
            private TextView mRetry;

            public ProductLoadStateViewHolder(@NonNull ViewGroup parent,
                                              @NonNull View.OnClickListener retryCallback){
                super(LayoutInflater.from(parent.getContext()).inflate(R.layout.load_state_item_rcv,parent,false));
                /*
                WHERE IS THE ITEMVIEW?
                 */
                LoadStateItemRcvBinding loadStateItemRcvBinding = LoadStateItemRcvBinding.bind(itemView);
                mErrorMsg = loadStateItemRcvBinding.errorMsg;
                mProgressBar = loadStateItemRcvBinding.progressBar;
                mRetry = loadStateItemRcvBinding.retryButton;
                mRetry.setOnClickListener(retryCallback);
            }
            public void bind (LoadState loadState){
                if(loadState instanceof  LoadState.Error){
                    LoadState.Error loadStateError = (LoadState.Error) loadState;
                    mErrorMsg.setText((loadStateError.getError().getLocalizedMessage()));
                }
                mProgressBar.setVisibility(
                        loadState instanceof LoadState.Loading ?View.VISIBLE :View.GONE);

                mRetry.setVisibility(
                        loadState instanceof LoadState.Error ?View.VISIBLE :View.GONE);

                mErrorMsg.setVisibility(
                        loadState instanceof LoadState.Error ?View.VISIBLE :View.GONE);
            }
    }
}
