import components.DateSelector;
import entity.DataStruct;
import mainlogic.ReUploadDataService;
import mainlogic.UploadDatasService;
import util.MyLogUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by rio on 2018/10/22.
 */
public class SwingDemo {

    private JPanel panel1;
    private JTextField Cpterminalid;
    private JTextField tableName;
    private JButton button;
    private JLabel titel;
    private JTextArea textArea1;
    private DateSelector startDatePicker;
    private DateSelector endDatePicker;

    public SwingDemo() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textArea1.setAutoscrolls(true);

                if (Cpterminalid.getText().trim() == null || "".equals(Cpterminalid.getText().trim())) {
                    textArea1.setText("车台号不能为空");
                    return;
                }

                if (tableName.getText().trim() == null || "".equals(tableName.getText().trim())) {
                    textArea1.setText("表名不能为空");
                    return;
                }

                System.out.println("click");

                DataStruct dataStruct = new DataStruct();
                dataStruct.getDataMap().put("Cpterminalid", Cpterminalid.getText().trim());
                System.out.println(Cpterminalid.getText().trim());
                dataStruct.getDataMap().put("tableName", tableName.getText().trim());
                System.out.println(tableName.getText().trim());

                String startStr = startDatePicker.getText().trim();
                String endStr = endDatePicker.getText().trim();

                try {
                    dataStruct.getDataMap().put("Startinfodatetime", MyLogUtil.sdfd.parse(startStr));
                } catch (Exception e1) {
                    e1.printStackTrace();
                    textArea1.setText("开始时间或开始时间毫秒数格式错误，开始时间格式为（yyyy-MM-dd HH:mm:ss）");
                    return;
                }
                try {
                    dataStruct.getDataMap().put("Endinfodatetime", MyLogUtil.sdfd.parse(endStr));
                } catch (Exception e1) {
                    e1.printStackTrace();
                    textArea1.setText("结束时间或结束时间毫秒数格式错误，结束时间格式为（yyyy-MM-dd HH:mm:ss）");
                    return;
                }
                dataStruct.getDataMap().put("DBUrl", "");

                MyLogUtil.accountExecute(dataStruct);

                // 线程启动方式
                // new UploadDatasService(textArea1, dataStruct).start();

                // 单一线程方式
                new ReUploadDataService(textArea1, dataStruct).reUploadData();

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("数据补传");
        frame.setContentPane(new SwingDemo().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
