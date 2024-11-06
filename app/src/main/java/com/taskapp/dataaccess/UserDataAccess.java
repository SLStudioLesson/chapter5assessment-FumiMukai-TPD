package com.taskapp.dataaccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.taskapp.model.User;

public class UserDataAccess {
    private final String filePath;

    public UserDataAccess() {
        filePath = "app/src/main/resources/users.csv";
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * 
     * @param filePath
     */
    public UserDataAccess(String filePath) {
        this.filePath = filePath;
    }

    /**
     * メールアドレスとパスワードを基にユーザーデータを探します。
     * 
     * @param email    メールアドレス
     * @param password パスワード
     * @return 見つかったユーザー
     */
    public User findByEmailAndPassword(String email, String password) {
        User user = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // タイトル行を読み飛ばす
            reader.readLine();

            // Code,Name,Email,Password
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                // メアド、パスワードが一致しなかったらスキップする
                if (!(values[2].equals(email) && values[3].equals(password)))
                    continue;

                // メアド、パスワードが一致
                int code = Integer.parseInt(values[0]);
                String name = values[1];
                String loginEmail = values[2];
                String loginPassword = values[3];

                user = new User(code, name, loginEmail, loginPassword);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ;
        }
        return user;
    }

    /**
     * コードを基にユーザーデータを取得します。
     * 
     * @param code 取得するユーザーのコード
     * @return 見つかったユーザー
     */
    public User findByCode(int code) {
        User user = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                int userCode = Integer.parseInt(values[0]);

                // 一致しない場合は終了
                if (code != userCode)
                    continue;

                // 一致する場合
                String name = values[1];
                String email = values[2];
                String password = values[3];

                user = new User(userCode, name, email, password);
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
