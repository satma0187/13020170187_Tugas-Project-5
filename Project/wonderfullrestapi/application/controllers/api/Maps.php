<?php
require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;
class Maps extends REST_Controller {

    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
        $this->load->model('m_login');
    }

    function index_post() {
        $idcode = $this->post('idcode');
        $kontak = $this->db->get_where('tb_peta', array('idcode' => $idcode))->result();
        $this->response($kontak, 200);
    }
}
?>