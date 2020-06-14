<?php

defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
use Restserver\Libraries\REST_Controller;

class Users extends REST_Controller {

    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
        $this->load->model('m_login');
    }

    //Menampilkan data kontak
    function index_get() {
        $id = $this->get('code');
        if ($id == '') {
            $kontak = $this->db->get('tb_users')->result();
        } else {
            $where = array(
                'id' => $this->get('code')
                );
            $kontak = $this->db->get_where('tb_users', $where)->result();
        }
        $this->response($id, 200);
    }

    function index_post() {
        $data = array(
                    'id'           => $this->post('id'),
                    'full_name'          => $this->post('full_name'),
                    'contact'    => $this->post('contact'),
                    'address'    => $this->post('address'),
                    'username'    => $this->post('username'),
                    'password'    => $this->post('password'),
                );
        $insert = $this->db->insert('tb_users', $data);
        $where = array(
			'username' => $this->post('username'),
			'password' => $this->post('password')
            );
        $counts = $this->db->get_where('tb_users', $where)->num_rows();
        if ($counts <= 1){
            if ($insert) {
                $kontak = $this->db->get_where('tb_users', $where)->row()->id;
                $this->response(array('status' => 'success', 502));

            } else {
                $this->response(array('status' => 'fail', 502));
            }
        } else {
            $this->response(array('status' => 'error', 502));
        }
        
    }

    function index_put() {
        $username = $this->put('username');
        $password = $this->put('password');
        $where = array(
			'username' => $username,
			'password' => $password
			);
        $cek = $this->m_login->cek_login("tb_users",$where)->num_rows();
        if($username != '' & $password != ''){
            if($cek > 0){
                $kontak = $this->db->get_where('tb_users', $where)->row()->id;
                $this->response(array('status' => 'success','id' => $kontak));
            }else{
                $this->response(array('status' => 'fail', 502));
            }
        } else {
            $this->response(array('status' => 'null', 502));
        }
		
    }
}
?>