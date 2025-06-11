<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\OrderDetail;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class OrderDetailController extends Controller
{
    public function index()
    {
        $details = OrderDetail::all();

        return response()->json([
            'success' => true,
            'message' => 'List semua detail pesanan.',
            'data'    => $details
        ], 200);
    }


    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'order_id'  => 'required|integer',
            'menu_id'   => 'required|integer',
            'quantity'  => 'required|integer',
            'subtotal'  => 'required|integer',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 422);
        }

        $detail = OrderDetail::create($request->all());

        return response()->json([
            'success' => true,
            'message' => 'Detail pesanan berhasil ditambahkan.',
            'data'    => $detail
        ], 201);
    }

    public function show(OrderDetail $orderDetail)
    {
        return response()->json([
            'success' => true,
            'message' => 'Detail pesanan ditemukan.',
            'data'    => $orderDetail
        ], 200);
    }

    public function update(Request $request, OrderDetail $orderDetail)
    {
        $validator = Validator::make($request->all(), [
            'order_id'  => 'integer',
            'menu_id'   => 'integer',
            'quantity'  => 'integer',
            'subtotal'  => 'integer',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 422);
        }

        $orderDetail->update($request->all());

        return response()->json([
            'success' => true,
            'message' => 'Detail pesanan berhasil diperbarui.',
            'data'    => $orderDetail
        ], 200);
    }

    public function destroy(OrderDetail $orderDetail)
    {
        $orderDetail->delete();

        return response()->json([
            'success' => true,
            'message' => 'Detail pesanan berhasil dihapus.'
        ], 200);
    }

}
