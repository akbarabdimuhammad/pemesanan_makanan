<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Order;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class OrderController extends Controller
{
    public function index()
    {
        $orders = Order::with('details')->orderBy('created_at', 'desc')->get();
        return response()->json([
            'success' => true,
            'message' => 'Daftar semua pesanan.',
            'data' => $orders
        ]);
    }

    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'user_id'    => 'required|integer',
            'total'      => 'required|integer',
            'status'     => 'required|string',
            'order_details' => 'required|array',
            'order_details.*.menu_id'  => 'required|integer',
            'order_details.*.quantity' => 'required|integer',
            'order_details.*.subtotal' => 'required|integer',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 422);
        }

        // Simpan order
        $order = Order::create([
            'user_id' => $request->user_id,
            'total'   => $request->total,
            'status'  => $request->status,
        ]);

        // Simpan detailnya
        foreach ($request->order_details as $detail) {
            $order->details()->create([
                'menu_id'  => $detail['menu_id'],
                'quantity' => $detail['quantity'],
                'subtotal' => $detail['subtotal'],
            ]);
        }

        return response()->json([
            'success' => true,
            'message' => 'Pesanan dan detailnya berhasil dibuat.',
            'data'    => $order->load('details.menu')
        ], 201);
    }


    public function show(Order $order)
    {
        $order->load('details.menu');
        return response()->json([
            'success' => true,
            'message' => 'Detail pesanan.',
            'data'    => $order
        ]);
    }

    public function update(Request $request, Order $order)
    {
        $order->update($request->all());
        return response()->json([
            'success' => true,
            'message' => 'Pesanan diperbarui.',
            'data'    => $order
        ]);
    }

    public function destroy(Order $order)
    {
        $order->delete();
        return response()->json([
            'success' => true,
            'message' => 'Pesanan dihapus.'
        ]);
    }
}
