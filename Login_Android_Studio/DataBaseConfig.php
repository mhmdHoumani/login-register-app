<?php

class DataBaseConfig
{
    public $host;
    public $username;
    public $password;
    public $database;

    public function __construct()
    {

        $this->host = 'localhost';
        $this->username = 'root';
        $this->password = '';
        $this->database = 'contacts';

    }
}

?>
