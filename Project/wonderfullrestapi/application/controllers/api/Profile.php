<?php
require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;
class Profile extends REST_Controller {

    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
        $this->load->model('m_login');
    }

    function index_get() {
        $id = $this->get('id');
        $this->db->where('id', $id);
        $kontak = $this->db->get('tb_users')->result();
        $this->response($kontak, 200);
    }

    //Menampilkan data kontak
    function index_post() {
        $id = $this->input->post('id');
        $fullName = $this->input->post('fullname');
        $contact = $this->input->post('contact');
        $address = $this->input->post('address');
        $username = $this->input->post('username');
        $password = $this->input->post('password');
        $data = array(
            'full_name'          => $fullName,
            'contact'    => $contact,
            'address'    => $address,
            'username'    => $username
        );
        $this->db->where('id', $id);
        $this->db->update('tb_users', $data);
        $this->response(array('status' => 'success', 'id' => $id, 200));
    }

    function index_delete($id){
        $this->db->where('id', $id);
        $this->db->delete('tb_users');
        $this->response(array('status' => 'success', 'id' => $id, 200));
    }
}
?>