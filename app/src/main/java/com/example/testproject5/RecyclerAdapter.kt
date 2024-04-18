package com.example.testproject5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private var pokemonList: List<Pokemon>, private var showHeight: Boolean): RecyclerView.Adapter<RecyclerAdapter.PokemonViewHolder>() {
    class PokemonViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val heightTextView: TextView = view.findViewById(R.id.heightTextView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.nameTextView.text = pokemon.name
        if (showHeight) {
            holder.heightTextView.visibility = View.VISIBLE
            holder.heightTextView.text = "Height: ${pokemon.height}"
        } else {
            holder.heightTextView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun updatePokemonList(newPokemonList: List<Pokemon>) {
        pokemonList = newPokemonList
        notifyDataSetChanged()
    }

    fun setShowHeight(showHeight: Boolean) {
        this.showHeight = showHeight
        notifyDataSetChanged()
    }
}