package com.example.netflixautologin;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class MyAccessibilityService extends AccessibilityService {

    //private final String email ="syash2581@gmail.com";
    private final String email ="colorhuntgame@gmail.com";
    private final String password ="qwertyuiop";
    @Override
    public void onServiceConnected() {
        //Toast.makeText(this,"In Service connected",Toast.LENGTH_LONG).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        /*AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            return;
        }*/

        // Grab the parent of the view that fired the event.
     AccessibilityNodeInfo rowNode = getRootInActiveWindow();
        if (rowNode == null) {
            return;
        }
        //Log.d("MYNETFLIX:rownode",rowNode.getChildCount()+"");
        for(int i=0;i<rowNode.getChildCount();i++)
        {
            AccessibilityNodeInfo childNode = rowNode.getChild(i);
            //Log.d("MYNETFLIX:rownode.gc",childNode.toString()+"");
            //rownode that has privacy,sign in textView which are clickable
            //after getting sign in page
            //there is a parent called scrollview so below 2nd if exploring that scrollview. to get edittext of email ,password and sign in button
            if(childNode.getText()!=null && childNode.getClassName().equals("android.widget.TextView") &&rowNode.getChild(i).getText().equals("SIGN IN"))
            {
                childNode.performAction(16);
                //https://developer.android.com/reference/android/view/accessibility/AccessibilityNodeInfo#ACTION_CLICK
                //visit this to have list of items
            }
            if(childNode!=null && childNode.getClassName().equals("android.widget.ScrollView"))
            {
                //getting count of child of scrollview which act as a form of html containing other elements.
                //Log.d("SSSSSSSSSSSSSSSSSSS",""+childNode.getChildCount());
                for(int x=0;x<childNode.getChildCount();x++)
                {
                    //traversing sub elements (input fields0 of scrollview
                    Log.d("jai jai bajrang bali",childNode.getChild(x).toString());
                    AccessibilityNodeInfo childNodeOfScrollView = childNode.getChild(x);
                    if(childNodeOfScrollView!=null)
                    {
                        if( childNodeOfScrollView.getClassName().equals("android.widget.EditText") && childNodeOfScrollView.getText().toString().equalsIgnoreCase("Email or phone number"))
                        {
                            changeText(childNodeOfScrollView,email);
                            Bundle arguments = new Bundle();
                        }
                        else if( childNodeOfScrollView.getClassName().toString().equals("android.widget.EditText") && childNodeOfScrollView.getText().toString().equalsIgnoreCase("Password"))
                        {
                            childNodeOfScrollView.performAction(1);
                            changeText(childNodeOfScrollView,password);
                            //childNodeOfScrollView.setEditable(false);
                        }
                        else if(childNodeOfScrollView.getClassName().toString().equalsIgnoreCase("android.widget.Button") && childNodeOfScrollView.getText().toString().equalsIgnoreCase("Sign In"))
                        {
                            //childNodeOfScrollView.performAction(1);
                           if(childNodeOfScrollView.performAction(16))
                           {
                               //this.setServiceInfo(new AccessibilityServiceInfo());
                               disableSelf();
                           }
                            //action code of click event
                           //https://developer.android.com/reference/android/view/accessibility/AccessibilityNodeInfo#ACTION_CLICK
                            // visit this to have list of items
                        }
                    }

                }
            }


        }

    }

    @Override
    public void onInterrupt() {

    }
    private void changeText(AccessibilityNodeInfo info,String value)
    {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData cd = ClipData.newPlainText("input",value);
        cm.setPrimaryClip(cd);
        info.performAction(32768);
    }
}
