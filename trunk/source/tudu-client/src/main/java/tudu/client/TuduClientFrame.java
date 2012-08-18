package tudu.client;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import tudu.web.ws.axis.AxisTuduLists;
import tudu.web.ws.axis.AxisTuduListsServiceLocator;
import tudu.web.ws.bean.WsTodo;
import tudu.web.ws.bean.WsTodoList;

/**
 * The Tudu Lists client GUI.
 * 
 * @author Julien Dubois
 */
public class TuduClientFrame extends JFrame {

    private static final long serialVersionUID = -7906881043568752604L;

    private JButton fetchTodoListButton = new JButton();

    private DefaultListModel listModel = new DefaultListModel();

    private JList todoLists = new JList(listModel);

    private JTextField wsUrl = new JTextField();

    private JLabel urlLabel = new JLabel();

    private JSeparator jSeparator1 = new JSeparator();

    private WsTodoList[] wsTodoLists = new WsTodoList[0];
    
    private AxisTuduLists client;
                               
    private DefaultTableModel todosTableModel = new DefaultTableModel(0, 3);
    
    private JTable todosTable = new JTable(todosTableModel);

    public TuduClientFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize(new Dimension(484, 335));
        this.setTitle( "Tudu Lists Client" );
        fetchTodoListButton.setText("Connect to server");
        fetchTodoListButton.setBounds(new Rectangle(15, 45, 175, 20));
        fetchTodoListButton.setActionCommand("fetchAllLists");
        fetchTodoListButton.addActionListener(new ActionListener() {
                                                  public void actionPerformed(ActionEvent e) {
                                                      fetchTodoListButton_actionPerformed(e);
                                                  }
                                              }
        );
        todoLists.setBounds(new Rectangle(15, 80, 450, 100));
        todoLists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        todoLists.addListSelectionListener(new TodoListSelectionListener());
        wsUrl.setBounds(new Rectangle(95, 12, 370, 20));
        wsUrl.setText("http://admin:admin@192.168.0.2:8080/tudu/ws/axis/TuduListsWebService");
        wsUrl.setToolTipText("The URL to the web service");
        urlLabel.setText("URL");
        urlLabel.setBounds(new Rectangle(15, 15, 55, 15));
        todosTable.setBounds(new Rectangle(15, 190, 450, 100));
        jSeparator1.setBounds(new Rectangle(5, 185, 465, 10));
        this.getContentPane().add(jSeparator1, null);
        this.getContentPane().add(todosTable, null);
        this.getContentPane().add(urlLabel, null);
        this.getContentPane().add(wsUrl, null);
        this.getContentPane().add(todoLists, null);
        this.getContentPane().add(fetchTodoListButton, null);
    }

    private void fetchTodoListButton_actionPerformed(ActionEvent event) {
        try {
            URL url = new URL(this.wsUrl.getText());
            AxisTuduListsServiceLocator locator = new AxisTuduListsServiceLocator();
            client = locator.getTuduListsWebService(url);
            listModel.clear();
            wsTodoLists = client.getAllTodoLists();
            for (int i = 0; i < wsTodoLists.length; i++) {
                listModel.addElement(wsTodoLists[i].getName());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    private class TodoListSelectionListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            try {
                if (e.getValueIsAdjusting() == false) {
                    while(todosTableModel.getRowCount() > 0) {
                        todosTableModel.removeRow(0);
                    }
                    String listId = wsTodoLists[todoLists.getSelectedIndex()].getListId();
                    WsTodo[] wsTodos = client.getTodosByTodoList(listId);
                    for (int i = 0; i < wsTodos.length; i++) {
                            Object[] row = {wsTodos[i].getDescription(), 
                            wsTodos[i].getPriority(), wsTodos[i].isCompleted()};
                            
                        todosTableModel.addRow(row);
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
