<?php
require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;
class Choose extends REST_Controller {

    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
        $this->load->model('m_login');
    }

    //Menampilkan data kontak
    function index_get() {
        $id = $this->get('id');
        if ($id == '') {
            $kontak = $this->db->get('tb_choose')->result();
        } else {
            $this->db->where('id', $id);
            $kontak = $this->db->get('tb_choose')->result();
        }
        $this->response($kontak, 200);
    }
}
?>