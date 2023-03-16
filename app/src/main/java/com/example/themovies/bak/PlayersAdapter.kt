package com.example.themovies.bak

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.R

class PlayersAdapter(
    private val context: Context,
    private val listTopPlayers: MutableList<PlayerData>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val CLUB_VIEW_TYPE = 1
        const val PLAYER_VIEW_TYPE = 2
    }

    class ClubsViewHolder(private val clubsView: View) : RecyclerView.ViewHolder(clubsView) {
        val txtClubName = clubsView.findViewById(R.id.txt_club_name) as TextView
    }

    class PlayersViewHolder(private val playersView: View) : RecyclerView.ViewHolder(playersView) {
        val txtPlayerName = playersView.findViewById(R.id.txt_player_name) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == CLUB_VIEW_TYPE) {
            return ClubsViewHolder(
                LayoutInflater.from(context).inflate(R.layout.header_view, parent, false)
            )
        } else {
            return PlayersViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemData = listTopPlayers[position]
        if (itemData.viewType == CLUB_VIEW_TYPE) {
            val clubsViewHolder = holder as ClubsViewHolder
            clubsViewHolder.txtClubName.text = itemData.textName
        } else {
            val playersViewHolder = holder as PlayersViewHolder
            playersViewHolder.txtPlayerName.text = itemData.textName
        }
    }

    override fun getItemViewType(position: Int): Int {
        return listTopPlayers[position].viewType
    }

    override fun getItemCount(): Int {
        return listTopPlayers.size
    }

}