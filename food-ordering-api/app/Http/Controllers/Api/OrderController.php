<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Order;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
    use Illuminate\Support\Facades\DB;

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
    $request->validate([
        'user_id' => 'required|integer',
        'total' => 'required|integer',
        'status' => 'required|string',
        'order_details' => 'required|array',
        'order_details.*.menu_id' => 'required|integer',
        'order_details.*.quantity' => 'required|integer',
        'order_details.*.subtotal' => 'required|integer',
    ]);

    DB::beginTransaction();

    try {
        $order = Order::create($request->only(['user_id', 'total', 'status']));

        foreach ($request->order_details as $detail) {
            $order->details()->create($detail);
        }

        DB::commit();

        return response()->json([
            'success' => true,
            'message' => 'Pesanan dan detail berhasil ditambahkan.',
            'data' => $order->load('details')
        ], 201);

    } catch (\Exception $e) {
        DB::rollBack();

        return response()->json([
            'success' => false,
            'message' => 'Gagal menyimpan pesanan',
            'error' => $e->getMessage()
        ], 500);
    }
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
