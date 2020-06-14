<?php
require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;
class Detail extends REST_Controller {

    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
        $this->load->model('m_login');
    }

    //Menampilkan data kontak
    function index_get() {
        $id = $this->get('id');
        if ($id == '') {
            $kontak = $this->db->get('tb_navigator')->result();
        } else {
            $this->db->where('id', $id);
            $kontak = $this->db->get('tb_navigator')->result();
        }
        $this->response($kontak, 200);
    }

    function index_post() {
        $id = $this->post('id');
        $idcode = $this->post('idcode');
        $code = $this->post('code');
        $kontak = $this->db->get_where('tb_navigator', array('idcode' => $idcode, 'code' => $code))->result();
        $this->response($kontak, 200);
    }
}
?>