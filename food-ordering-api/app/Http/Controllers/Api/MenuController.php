<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Menu;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class MenuController extends Controller
{
    /**
     * Menampilkan semua data menu.
     */
    public function index()
    {
        $menus = Menu::orderBy('created_at', 'desc')->get();
        return response()->json([
            'success' => true,
            'message' => 'Daftar semua menu.',
            'data' => $menus,
        ], 200);
    }

    /**
     * Menyimpan menu baru.
     */
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'name'      => 'required|string|max:255',
            'price'     => 'required|integer',
            'category'  => 'required|string',
            'description' => 'nullable|string',
            'image_url' => 'nullable|url',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 422);
        }

        $menu = Menu::create($request->all());

        return response()->json([
            'success' => true,
            'message' => 'Menu berhasil ditambahkan!',
            'data' => $menu,
        ], 201);
    }

    /**
     * Menampilkan satu menu spesifik.
     */
    public function show(Menu $menu)
    {
        return response()->json([
            'success' => true,
            'message' => 'Detail menu.',
            'data' => $menu,
        ], 200);
    }

    /**
     * Memperbarui menu.
     */
    public function update(Request $request, Menu $menu)
    {
        $validator = Validator::make($request->all(), [
            'name'      => 'string|max:255',
            'price'     => 'integer',
            'category'  => 'string',
            'description' => 'nullable|string',
            'image_url' => 'nullable|url',
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 422);
        }

        $menu->update($request->all());

        return response()->json([
            'success' => true,
            'message' => 'Menu berhasil diperbarui!',
            'data' => $menu,
        ], 200);
    }

    /**
     * Menghapus menu.
     */
    public function destroy(Menu $menu)
    {
        $menu->delete();

        return response()->json([
            'success' => true,
            'message' => 'Menu berhasil dihapus!',
        ], 200);
    }
}