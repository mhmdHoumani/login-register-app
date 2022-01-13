<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $host;
    protected $DB_username;
    protected $DB_password;
    protected $database;

    public function __construct()
    {
        $dbc = new DataBaseConfig();
        $this->host = $dbc->host;
        $this->DB_username = $dbc->username;
        $this->DB_password = $dbc->password;
        $this->database = $dbc->database;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->host, $this->DB_username, $this->DB_password, $this->database);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql =
        "SELECT *
		FROM contact AS C 
		WHERE C.username = '{$username}' 
		AND C.password = '{$password}'";

        $result = mysqli_query($this->connect, $this->sql);
        $rows = mysqli_num_rows($result);
        return $rows == 1;
    }

    /*function signUp($table, $fullname, $email, $username, $password)
    {
        $fullname = $this->prepareData($fullname);
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $email = $this->prepareData($email);
        $password = password_hash($password, PASSWORD_DEFAULT);
        $this->sql =
            "INSERT INTO " . $table . " (fullname, username, password, email) VALUES ('" . $fullname . "','" . $username . "','" . $password . "','" . $email . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }*/

}

?>
