package live.itrip.app.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import live.itrip.app.R;
import live.itrip.app.data.model.MessageModel;
import live.itrip.common.util.StringUtils;

/**
 * Created by Feng on 2017/6/27.
 */

public class MessageRecyclerAdapter extends BaseQuickAdapter<MessageModel> {

    public MessageRecyclerAdapter(List<MessageModel> data) {
        super(R.layout.item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, MessageModel msg) {
        holder.setText(R.id.tv_user_name, StringUtils.replaceAllBlank(msg.getUserName()));
        holder.setText(R.id.identityView, StringUtils.replaceAllBlank(msg.getReadme()));
        holder.setText(R.id.tv_time, StringUtils.trimNewLine(msg.getCreateTime().toString()));
        holder.setText(R.id.tv_content, StringUtils.trimNewLine(msg.getContent()));
    }
}