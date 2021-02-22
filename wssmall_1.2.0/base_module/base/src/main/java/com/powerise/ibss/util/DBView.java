/*
 * Created on 2005-2-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.powerise.ibss.util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DBView  extends JFrame {
	private static Logger logger = Logger.getLogger(DBView.class);
    private Connection m_Conn=null;
    JPanel contentPane;
    JTextArea edSQL = new JTextArea(10,2);
    JTable tResult = new JTable();
    BorderLayout borderLayout1 = new BorderLayout();
    String m_SQL =null;
    
    public DBView()
    {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    private void SetConnection(Connection conn )
    {
        m_Conn = conn;
    }
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(borderLayout1);
        setSize(new Dimension(616, 523));
        setTitle("DB Viewer");
        edSQL.setFont(new java.awt.Font("Courier New", Font.PLAIN, 12));
        edSQL.setBorder(BorderFactory.createLoweredBevelBorder());
        edSQL.setText("");
        edSQL.setTabSize(4);
        tResult.setBorder(BorderFactory.createLoweredBevelBorder());
        borderLayout1.setHgap(2);
        borderLayout1.setVgap(2);
        contentPane.add(tResult, java.awt.BorderLayout.CENTER);
        contentPane.add(edSQL, java.awt.BorderLayout.NORTH);
        edSQL.addKeyListener(new DBView_edSQL_keyAdapter(this));

        edSQL.setSize(new Dimension(616, 200));
    }
    public void edSQL_keyPressed(KeyEvent e) {
        if(e.getKeyCode() ==KeyEvent.VK_F9)
        {
           m_SQL = edSQL.getText();
           execute();
        }
    }
    private void execute()
    {
        DefaultTableModel tableModel = (DefaultTableModel) tResult.getModel();
        TableColumn tCol =null;
       
       logger.info("column coutn:"+tableModel.getColumnCount());
        TableColumn col = new TableColumn(tableModel.getColumnCount());

        
        tCol = new TableColumn(0);
        tCol.setHeaderValue("kljsdf");
        tableModel.addColumn(tCol);
        
        tCol = new TableColumn(1);
        tCol.setHeaderValue("kljsdf");
        tableModel.addColumn(tCol);
     
      
        
        
       

      //      for (int i = 0; i < count; i++) {
            String line[] =  { "ksdljf","wwww"};
                   
            
            tableModel.addRow(line);


        
    }
    public static  void start(Connection conn){
        
       
        try{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e)
        {
            e.printStackTrace();
            return ;
        }
        //SwingUtilities.invokeLater(new Runnable() {
        DBView vDB = new DBView();
        vDB.SetConnection(conn);
        vDB.invalidate();
//      Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = vDB.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        vDB.setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);
        vDB.setVisible(true);
        
	}

}



class DBView_edSQL_keyAdapter extends KeyAdapter {
    private DBView adaptee;
    DBView_edSQL_keyAdapter(DBView adaptee) {
        this.adaptee = adaptee;
    }

    @Override
	public void keyPressed(KeyEvent e) {
        adaptee.edSQL_keyPressed(e);
    }
}
