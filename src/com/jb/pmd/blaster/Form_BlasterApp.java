package com.jb.pmd.blaster;

import java.awt.Cursor;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Form_BlasterApp extends javax.swing.JFrame
{
    public Form_BlasterApp()
    {
        initComponents();
        
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent e) 
            { 
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                jMenuItem7.setEnabled(!lsm.isSelectionEmpty());
                jMenuItem8.setEnabled(!lsm.isSelectionEmpty());
                jMenuItem9.setEnabled(!lsm.isSelectionEmpty());
                jMenuItem10.setEnabled(!lsm.isSelectionEmpty());
                
                jButton1.setEnabled(!lsm.isSelectionEmpty());
                
                refreshList();
            }
        });
        
        jList1.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent e) 
            { 
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                jMenuItem11.setEnabled(!lsm.isSelectionEmpty());
                jMenuItem12.setEnabled(!lsm.isSelectionEmpty());
                jMenuItem13.setEnabled(!lsm.isSelectionEmpty());
                jMenuItem14.setEnabled(!lsm.isSelectionEmpty());
            }
        });
        
        jTextArea1.getDocument().addDocumentListener(new DocumentListener() 
        {

            @Override
            public void insertUpdate(DocumentEvent e) {
                jLabel1.setText(Integer.toString(jTextArea1.getText().length()));
                jCheckBox2.setEnabled(jTextArea1.getText().length() <= 160);         
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                jLabel1.setText(Integer.toString(jTextArea1.getText().length()));
                jCheckBox2.setEnabled(jTextArea1.getText().length() <= 160);     
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                jLabel1.setText(Integer.toString(jTextArea1.getText().length()));
                jCheckBox2.setEnabled(jTextArea1.getText().length() <= 160);
            }
        });
        
        if(!BlasterEngine.DEBUG)
            jMenu3.setVisible(false);
        
        setLocationRelativeTo(null);

        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        
        BlasterEngine.initBlaster();
        
        this.refreshRosters();
        this.refreshList();
        
        if(BlasterEngine.googleuser.equals("")) 
            new Form_Preferences(this, true).setVisible(true);

    }
    
    public void refreshTheme()
    {        
        try {
            javax.swing.UIManager.setLookAndFeel((BlasterEngine.theme.equals(""))
                ?javax.swing.UIManager.getSystemLookAndFeelClassName()
                :BlasterEngine.theme);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println("[WARNING] Could not change theme.");
        }
        
        javax.swing.SwingUtilities.updateComponentTreeUI(this);
    }
    
    private void refreshRosters()
    {
        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, 
                new String[] {"Rosters"});

        for(String s : BlasterEngine.getRosters())
            model.addRow(new Object[] {s});

        jTable1.setModel(model);
    }
    
    private void refreshList()
    {
        DefaultListModel model = new DefaultListModel();
        if(jTable1.getSelectedRow() > -1)
        {
            LinkedList<Contact> bros = BlasterEngine.getRoster(
                    (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0));

            if(bros != null)
                for(Contact c : bros)
                    model.addElement(c);
        }
        
        jList1.setModel(model);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem15 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PMD Announce");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextArea1InputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jTextArea1CaretPositionChanged(evt);
            }
        });
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Send");
        jButton1.setEnabled(false);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Email");

        jCheckBox2.setSelected(true);
        jCheckBox2.setText("Text Message");

        jCheckBox3.setSelected(true);
        jCheckBox3.setText("Facebook");
        jCheckBox3.setEnabled(false);

        jLabel1.setText("0");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setTableHeader(null);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable1);

        jLabel2.setText("Contacts");

        jLabel3.setText("Message");

        jLabel4.setText("Rosters");

        jMenu1.setText("File");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem3.setText("Preferences...");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Roster");

        jMenuItem6.setText("New Roster");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuItem1.setText("Import Roster");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem1);
        jMenu4.add(jSeparator1);

        jMenuItem7.setText("Rename Roster");
        jMenuItem7.setEnabled(false);
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuItem9.setText("Duplicate Roster");
        jMenuItem9.setEnabled(false);
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem8.setText("Delete Roster");
        jMenuItem8.setEnabled(false);
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);
        jMenu4.add(jSeparator2);

        jMenuItem10.setText("Add Contact");
        jMenuItem10.setEnabled(false);
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem12.setText("Remove Contact");
        jMenuItem12.setEnabled(false);
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuItem11.setText("Edit Contact");
        jMenuItem11.setEnabled(false);
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);
        jMenu4.add(jSeparator3);

        jMenuItem14.setText("Move Contact To...");
        jMenuItem14.setEnabled(false);
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuItem13.setText("Copy Contact To...");
        jMenuItem13.setEnabled(false);
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem13);

        jMenuBar1.add(jMenu4);

        jMenu3.setText("Debug");

        jMenuItem18.setText("Refresh");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem18);
        jMenu3.add(jSeparator4);

        jMenuItem4.setText("Purge Rosters");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setText("Purge Preferences");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);
        jMenu3.add(jSeparator6);

        jMenuItem15.setText("Crash");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem15);
        jMenu3.add(jSeparator7);

        jMenuItem17.setText("Enable Unstable Components");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem17);

        jMenuItem16.setText("Browser Test");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem16);
        jMenu3.add(jSeparator8);

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Use Debug Database");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 335, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jCheckBox1)
                            .addComponent(jCheckBox2)
                            .addComponent(jCheckBox3)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextArea1CaretPositionChanged(java.awt.event.InputMethodEvent evt)                                                
    {}                                               

    private void jTextArea1InputMethodTextChanged(java.awt.event.InputMethodEvent evt)                                                  
    {}  
    
    private void jTextArea1KeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextArea1KeyTyped
    {//GEN-HEADEREND:event_jTextArea1KeyTyped

    }//GEN-LAST:event_jTextArea1KeyTyped
    
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt)                                      
    {}
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem2ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
        Form_ImportRoster ar = new Form_ImportRoster(this, true);
        
        ar.setVisible(true);
        
        this.refreshRosters();
        this.refreshList();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem3ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem3ActionPerformed
        new Form_Preferences(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        if(jTable1.getSelectedRow() > -1)
        {
            final LinkedList<Contact> bros = BlasterEngine.getRoster(
                    (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0));

            final String msg = jTextArea1.getText();
            final boolean email = jCheckBox1.isEnabled()
                                  && jCheckBox1.isSelected(),                              
                          text  = jCheckBox2.isEnabled() 
                                  && jCheckBox2.isSelected(),
                          fbook = jCheckBox3.isEnabled()
                                  && jCheckBox3.isSelected();

            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            Form_Waiting.wait(this, "Sending messages...", new com.jb.pmd.blaster.Form_Waiting.Waitable()
            {
                @Override
                public void execute()
                {
                    if (email)
                        BlasterEngine.sendEmails(msg, bros);
                    if (text)
                        BlasterEngine.sendSMS(msg, bros);
                    if (fbook)
                        BlasterEngine.postFacebook(msg);
                }
            });

            jTextArea1.setText("");
            jLabel1.setText("0");

            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem4ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem4ActionPerformed
        BlasterEngine.purgeRosters(true, true, true);
        DatabaseEngine.purgeRosters(true, true, true);
        System.out.println("Rosters purged from database.");
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem5ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem5ActionPerformed
        BlasterEngine.purgePreferences(true, true, true);
        DatabaseEngine.purgePreferences(true, true, true);
        System.out.println("Preferences purged from database.");
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTable1MouseClicked
    {//GEN-HEADEREND:event_jTable1MouseClicked

    }//GEN-LAST:event_jTable1MouseClicked

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        String newRoster = Form_RosterName.getRosterName(this, "NewRoster");

        if(!newRoster.equals(""))
        {
            LinkedList<Contact> roster = new LinkedList();

            BlasterEngine.addRoster(newRoster, roster);
            DatabaseEngine.saveRoster(newRoster, roster);

            System.out.println(newRoster + " created.");
        }
        
        this.refreshRosters();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        if(jTable1.getSelectedRow() > -1)
        {
            String  oldName = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0),
                    newName = Form_RosterName.getRosterName(this, oldName);
        
            BlasterEngine.addRoster(newName, BlasterEngine.getRoster(oldName));
            BlasterEngine.removeRoster(oldName);
            
            DatabaseEngine.removeRoster(oldName);
            DatabaseEngine.saveRoster(newName, BlasterEngine.getRoster(newName));
            
            System.out.println("Renaming " + oldName + " to " + newName + ".");
        }
        
        this.refreshRosters();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        if(jTable1.getSelectedRow() > -1)
        {
            String roster = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0);
            Contact c = Form_Contact.getContact(this, null);
            
            if( c != null)
            {            
                BlasterEngine.addContact(roster, c);
                DatabaseEngine.saveContact(roster, c);
                System.out.println("Adding " + c.first + " to the roster.");
            }
            
            this.refreshList();
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        int _ = 1/0;
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        if(jTable1.getSelectedRow() > -1)
        {
            String roster = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0);
            
            BlasterEngine.removeRoster(roster);
            DatabaseEngine.removeRoster(roster);

            System.out.println("Removing " + roster + ".");
        }
        
        this.refreshRosters();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void editContact()
    {
        if(jTable1.getSelectedRow() > -1 && jList1.getSelectedIndex() > -1)
        {
            String roster = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0);
            Contact c = Form_Contact.getContact(this, (Contact)jList1.getSelectedValue());
            
            if( c != null)
            {
                BlasterEngine.removeContact(roster, (Contact)jList1.getSelectedValue());
                BlasterEngine.addContact(roster, c);
                
                DatabaseEngine.saveRoster(roster, BlasterEngine.getRoster(roster));
                
                System.out.println("Changed " + c.first + "'s information.");
            }
            
            this.refreshList();
        }
    }
    
    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        editContact();
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        if(jTable1.getSelectedRow() > -1)
        {
            String  oldName = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0),
                    newName = Form_RosterName.getRosterName(this, "Copy of " + oldName);
        
            LinkedList<Contact> oldList = BlasterEngine.getRoster(oldName);
            LinkedList<Contact> newList = new LinkedList();
            
            if(oldList != null)
            {
                for(Contact c : oldList)
                        newList.add(new Contact(c));
                    
                BlasterEngine.addRoster(newName, newList);
                DatabaseEngine.saveRoster(newName, BlasterEngine.getRoster(newName));
                System.out.println("Duplicated " + oldName + " as " + newName + ".");
            }        
        }
        
        this.refreshRosters();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        if(jTable1.getSelectedRow() > -1 && jList1.getSelectedIndex() > -1)
        {
            String roster = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0);
            Contact contact = (Contact)jList1.getSelectedValue();
            
            BlasterEngine.removeContact(roster, contact);
            DatabaseEngine.removeContact(roster, contact);

            System.out.println("Removing " + contact.first + " from the roster.");
          
            this.refreshList();
        } 
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        try
        {
            java.awt.Desktop.getDesktop().browse(new java.net.URI("http://www.google.com/"));
        }
        catch(IOException | URISyntaxException e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        if(jTable1.getSelectedRow() > -1 && jList1.getSelectedIndex() > -1)
        {
            String oldRoster = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 0);
            String newRoster = Form_SelectRoster.getRoster(this);
            Contact c = (Contact)jList1.getSelectedValue();
            
            if( !newRoster.equals(""))
            {
                BlasterEngine.addContact(newRoster, c);
                BlasterEngine.removeContact(oldRoster, (Contact)jList1.getSelectedValue());
                
                DatabaseEngine.saveRoster(oldRoster, BlasterEngine.getRoster(oldRoster));
                DatabaseEngine.saveRoster(newRoster, BlasterEngine.getRoster(newRoster));
                
                System.out.println("Moved " + c.first + " to " + newRoster +".");
            }
            
            this.refreshList();
        }
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        if(jTable1.getSelectedRow() > -1 && jList1.getSelectedIndex() > -1)
        {
            String newRoster = Form_SelectRoster.getRoster(this);
            Contact c = (Contact)jList1.getSelectedValue();
            
            if( !newRoster.equals(""))
            {
                BlasterEngine.addContact(newRoster, new Contact(c));
                
                DatabaseEngine.saveRoster(newRoster, BlasterEngine.getRoster(newRoster));
                
                System.out.println("Copied " + c.first + " to " + newRoster +".");
            }
            
            this.refreshList();
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        jCheckBox3.setEnabled(true);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        this.refreshRosters();
        this.refreshList();
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if (evt.getClickCount() == 2)
            editContact();
    }//GEN-LAST:event_jList1MouseClicked

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        DatabaseEngine.disconnect();
        DatabaseEngine.database = (!jCheckBoxMenuItem1.isSelected())?"data.db":"debug.db";
        
        BlasterEngine.initBlaster();
        
        this.refreshTheme();
        this.refreshRosters();
        this.refreshList();
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    public static void main(String args[])
    {
        for(String s : args)
            if(s.equalsIgnoreCase("--debug"))
            {
                BlasterEngine.DEBUG = true;
                DatabaseEngine.database = "debug.db";
            }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new Form_BlasterApp().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
