package com.calc3d.app.dialogs;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.calc3d.app.resources.Icons;

import java.applet.Applet;
import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/***
 * A simple help class made by extending the JFrame.
 * @author Arnab
 */
public class HelpDialog extends JDialog {
    private URL contents;
    private JEditorPane ep;
    private ArrayList urlHistory=new ArrayList();
    private int historyIndex=-1;
    private JButton btnContents,btnBack,btnForward;
    private void enableButtons() {
        btnBack.setEnabled(historyIndex>0);
        btnForward.setEnabled(historyIndex<urlHistory.size()-1);
    }
    private void setURL(URL url) {
        try {
            ep.setPage(url);
            if (historyIndex<0 || !url.equals((URL)urlHistory.get(historyIndex))) {
                urlHistory.add(url);
                historyIndex++;
                while(urlHistory.size()>historyIndex+1)
                   urlHistory.remove(historyIndex);
                enableButtons();
            }
        } catch(IOException t) { }
    }
    private void goBack() {
        if (historyIndex>0) setURL((URL)urlHistory.get(--historyIndex));
        enableButtons();
    }
    private void goForward() {
        if (historyIndex<urlHistory.size()-1) setURL((URL)urlHistory.get(++historyIndex));
        enableButtons();
    }
    public HelpDialog(Window window , String title,String htmlFile) {
        super(window,title,ModalityType.APPLICATION_MODAL);
    	this.setTitle(title);
        Container contentPane=getContentPane();
        ep=new JEditorPane();
        ep.setEditable(false);
        ep.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    setURL(e.getURL());
                }
            }
        });
        ActionListener buttonsListener=new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cmd=e.getActionCommand();
                if (cmd.equals("Contents")) setURL(contents);
                else if (cmd.equals("Back")) goBack();
                else if (cmd.equals("Forward")) goForward();
            }
        };
        final JToolBar toolBar=new JToolBar();
        btnContents=new JButton(Icons.HOME);
        toolBar.add(btnContents); btnContents.addActionListener(buttonsListener);
        btnContents.setActionCommand("Contents");
        btnBack=new JButton(Icons.BACK); 
        toolBar.add(btnBack); btnBack.addActionListener(buttonsListener);
        btnBack.setActionCommand("Back");
        btnForward=new JButton(Icons.FORWARD);
        toolBar.add(btnForward); btnForward.addActionListener(buttonsListener);
        btnForward.setActionCommand("Forward");
        contentPane.add(toolBar,BorderLayout.NORTH);
        contentPane.add(new JScrollPane(ep));
        setSize(480,560);
        setLocationRelativeTo(null);
        contents=this.getClass().getResource("/com/calc3d/app/resources/readme.html");
        setURL(contents);
    }
}