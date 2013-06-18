package com.omrlnr.jreddit;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import com.omrlnr.jreddit.utils.Utils;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * This class represents a reddit message
 *
 * @author <a href="https://github.com/jasonsimpson">Jason Simpson</a>
 * 
 */
public class Message extends Thing {

    public Message(JSONObject jsonObj) {
        super(jsonObj);
    }

    public String toString() {
        return toString("");
    }

    public String toString(String indent) {
        String thing = super.toString(indent);
        return thing + 
            indent + "   Comment:       "   + getBody()     + "\n" +
            indent + "       author:    "   + getAuthor()   + "\n" +
            indent + "       parent_id: "   + getParentId()   + "\n";
            // indent + Utils.getJSONDebugString(_data, indent);

    }

    public String getBody() { 
        return (String)((JSONObject)_data.get("data")).get("body");
    }

    public String getParentId() { 
        return (String)((JSONObject)_data.get("data")).get("parent_id");
    }

    public String getAuthor() { 
        return (String)((JSONObject)_data.get("data")).get("author");
    }

    /**
     * Get the replies to this message.
     * TODO not sure if this is applicable.
     */
    public List<Comment> getReplies() {
        List<Comment> ret = new ArrayList();
        
        JSONObject data = (JSONObject)_data.get("data");

        //
        // DEBUG
        //
        // System.out.println(Utils.getJSONDebugString(data));

        //
        // Need to check type of "replies" property.
        // This is annoying. Rather than an empty object, if no replies
        // are present the JSON tools treat this as a String. Therefore
        // need to check instanceof on the "replies" property.
        //
        Object repliesObj = data.get("replies");
        if(repliesObj instanceof JSONObject) {

            JSONObject replies = (JSONObject)repliesObj;
            JSONObject replyData = (JSONObject)replies.get("data");
            JSONArray children = (JSONArray)replyData.get("children");
    
            for (int i = 0; i < children.size(); i++) {
                JSONObject jsonData = (JSONObject)children.get(i);
                Comment comment = new Comment(jsonData);
    
                ret.add(new Comment(jsonData));
            }
        }

        return ret;
    }

}