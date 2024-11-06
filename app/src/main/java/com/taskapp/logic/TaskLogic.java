package com.taskapp.logic;

import java.util.List;

import com.taskapp.dataaccess.LogDataAccess;
import com.taskapp.dataaccess.TaskDataAccess;
import com.taskapp.dataaccess.UserDataAccess;
import com.taskapp.model.Task;
import com.taskapp.model.User;
import com.taskapp.exception.AppException;

public class TaskLogic {
    private final TaskDataAccess taskDataAccess;
    private final LogDataAccess logDataAccess;
    private final UserDataAccess userDataAccess;

    public TaskLogic() {
        taskDataAccess = new TaskDataAccess();
        logDataAccess = new LogDataAccess();
        userDataAccess = new UserDataAccess();
    }

    /**
     * 自動採点用に必要なコンストラクタのため、皆さんはこのコンストラクタを利用・削除はしないでください
     * 
     * @param taskDataAccess
     * @param logDataAccess
     * @param userDataAccess
     */
    public TaskLogic(TaskDataAccess taskDataAccess, LogDataAccess logDataAccess, UserDataAccess userDataAccess) {
        this.taskDataAccess = taskDataAccess;
        this.logDataAccess = logDataAccess;
        this.userDataAccess = userDataAccess;
    }

    /**
     * 全てのタスクを表示します。
     *
     * @see com.taskapp.dataaccess.TaskDataAccess#findAll()
     * @param loginUser ログインユーザー
     */
    public void showAll(User loginUser) {
        // findAllでデータ取得
        List<Task> tasks = taskDataAccess.findAll();
        // 取得したデータを表示
        // 1. タスク名：taskA, 担当者名：あなたが担当しています, ステータス：未着手
        tasks.forEach(task -> {
            String showRepUser;
            String showStatus;
            String loginEmail = loginUser.getEmail();
            User repUser = userDataAccess.findByCode(task.getRepUser().getCode());
            String repUserEmail = repUser.getEmail();
            if (!(loginEmail.equals(repUserEmail))) {
                repUser = userDataAccess.findByCode(task.getRepUser().getCode());
                showRepUser = repUser.getName() + "が担当しています";
            } else {
                showRepUser = "あなたが担当しています";
            }
            if (task.getStatus() == 2) {
                showStatus = "完了";
            } else if (task.getStatus() == 1) {
                showStatus = "着手中";
            } else {
                showStatus = "未着手";
            }
            System.out.println(
                    task.getCode() + ". タスク名：" + task.getName() + ", 担当者名：" + showRepUser + ", ステータス：" + showStatus);
        });
    }

    /**
     * 新しいタスクを保存します。
     *
     * @see com.taskapp.dataaccess.UserDataAccess#findByCode(int)
     * @see com.taskapp.dataaccess.TaskDataAccess#save(com.taskapp.model.Task)
     * @see com.taskapp.dataaccess.LogDataAccess#save(com.taskapp.model.Log)
     * @param code        タスクコード
     * @param name        タスク名
     * @param repUserCode 担当ユーザーコード
     * @param loginUser   ログインユーザー
     * @throws AppException ユーザーコードが存在しない場合にスローされます
     */
    public void save(int code, String name, int repUserCode, User loginUser) throws AppException {
        User repUser = userDataAccess.findByCode(repUserCode);
        if (repUser == null) {
            throw new AppException("存在するユーザーコードを入力してください");
        }
        Task task = new Task(code, name, 0, repUserCode);
        taskDataAccess.save(task);
        System.out.print("taskEの登録が完了しました。");
    }

    /**
     * タスクのステータスを変更します。
     *
     * @see com.taskapp.dataaccess.TaskDataAccess#findByCode(int)
     * @see com.taskapp.dataaccess.TaskDataAccess#update(com.taskapp.model.Task)
     * @see com.taskapp.dataaccess.LogDataAccess#save(com.taskapp.model.Log)
     * @param code      タスクコード
     * @param status    新しいステータス
     * @param loginUser ログインユーザー
     * @throws AppException タスクコードが存在しない、またはステータスが前のステータスより1つ先でない場合にスローされます
     */
    // public void changeStatus(int code, int status,
    // User loginUser) throws AppException {
    // }

    /**
     * タスクを削除します。
     *
     * @see com.taskapp.dataaccess.TaskDataAccess#findByCode(int)
     * @see com.taskapp.dataaccess.TaskDataAccess#delete(int)
     * @see com.taskapp.dataaccess.LogDataAccess#deleteByTaskCode(int)
     * @param code タスクコード
     * @throws AppException タスクコードが存在しない、またはタスクのステータスが完了でない場合にスローされます
     */
    // public void delete(int code) throws AppException {
    // }
}