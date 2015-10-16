package utility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.KeyStroke;
import swing.slyumCustomizedComponents.STable;

public class EditableCellFocusAction extends WrappedAction implements ActionListener {
  private STable table;

  /* Specify the component and KeyStroke for the Action we want to wrap */
  public EditableCellFocusAction(STable table, KeyStroke keyStroke) {
    super(table, keyStroke);
    this.table = table;
  }

  /* Provide the custom behaviour of the Action */
  @Override
  public void actionPerformed(ActionEvent e) {
    int originalRow = table.getSelectedRow();
    int originalColumn = table.getSelectedColumn();

    invokeOriginalAction(e);

    int row = table.getSelectedRow();
    int column = table.getSelectedColumn();
    
    if (originalColumn == table.getColumnCount() - 1 && column == 0 &&
        originalRow == table.getRowCount() - 1 && row == 0) {
      table.addRow();
      return;
    }

    // Keep invoking the original action until we find an editable cell
    while (!table.isCellEditable(row, column)) {
      invokeOriginalAction(e);
      

      // We didn't move anywhere, reset cell selection and get out.
      if (row == table.getSelectedRow() && column == table.getSelectedColumn()) {
        table.changeSelection(originalRow, originalColumn, false, false);
        break;
      }

      row = table.getSelectedRow();
      column = table.getSelectedColumn();

      // Back to where we started, get out.
      if (row == originalRow && column == originalColumn) {
        break;
      }
    }
  }
}
