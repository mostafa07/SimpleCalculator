package com.mx3.thirdwayvsimplecalculator.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.mx3.thirdwayvsimplecalculator.BR;
import com.mx3.thirdwayvsimplecalculator.R;
import com.mx3.thirdwayvsimplecalculator.data.model.Operation;

import java.util.List;

public class OperationRecordAdapter extends RecyclerView.Adapter<OperationRecordAdapter.OperationRecordViewHolder> {

    private static final String LOG_TAG = OperationRecordAdapter.class.getSimpleName();
    private static final int INSERT_POSITION = 0;

    private List<Operation> mDataList;
    private OperationRecordClickHandler mClickHandler;

    // Constructor

    public OperationRecordAdapter(OperationRecordClickHandler clickHandler, List<Operation> mDataList) {
        this.mClickHandler = clickHandler;
        this.mDataList = mDataList;
    }


    // Click Handler Interface

    public interface OperationRecordClickHandler {

        void onItemClick(int position);
    }


    // Adapter Overridden Methods

    @NonNull
    @Override
    public OperationRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final ViewDataBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new OperationRecordViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OperationRecordViewHolder holder, int position) {
        holder.bind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_operation_record;
    }


    // Getters and Setters

    public List<Operation> getDataList() {
        return mDataList;
    }

    public void setDataList(List<Operation> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
    }


    // Other Methods

    public void addOperationRecord(Operation operation) {
        mDataList.add(INSERT_POSITION, operation);
        notifyItemInserted(INSERT_POSITION);
    }

    public void removeLastOperation() {
        if (!mDataList.isEmpty()) {
            this.mDataList.remove(INSERT_POSITION);
            notifyItemRemoved(INSERT_POSITION);
        }
    }

    public void removeOperationRecordAtPosition(int position) {
        if (!mDataList.isEmpty() && mDataList.size() >= position) {
            mDataList.remove(position);
            notifyItemRemoved(position);
        }
    }


    // View Holder Class

    class OperationRecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ViewDataBinding mViewDataBinding;

        public OperationRecordViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            mViewDataBinding = viewDataBinding;

            viewDataBinding.getRoot().setOnClickListener(this);
        }

        public void bind(Operation operation) {
            mViewDataBinding.setVariable(BR.operation, operation);
            mViewDataBinding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onItemClick(getAdapterPosition());
        }
    }
}
