package com.example.themovies.bak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.R

class MainActivity1 : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var listTopPlayers: MutableList<PlayerData> = mutableListOf<PlayerData>()
    private var playersAdapter: PlayersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        listTopPlayers = mutableListOf()

        listTopPlayers.add(PlayerData(PlayersAdapter.CLUB_VIEW_TYPE,"Real Madrid"))
        listTopPlayers.add(PlayerData(PlayersAdapter.PLAYER_VIEW_TYPE,"Sergio Ramos"))
        listTopPlayers.add(PlayerData(PlayersAdapter.PLAYER_VIEW_TYPE,"Karim Benzema"))
        listTopPlayers.add(PlayerData(PlayersAdapter.CLUB_VIEW_TYPE,"Barcelona"))
        listTopPlayers.add(PlayerData(PlayersAdapter.PLAYER_VIEW_TYPE,"Lionel Messi"))
        listTopPlayers.add(PlayerData(PlayersAdapter.CLUB_VIEW_TYPE,"Bayern Munich"))
        listTopPlayers.add(PlayerData(PlayersAdapter.PLAYER_VIEW_TYPE,"Robert Lewandowski"))
        listTopPlayers.add(PlayerData(PlayersAdapter.PLAYER_VIEW_TYPE,"Manuel Neuer"))
        listTopPlayers.add(PlayerData(PlayersAdapter.CLUB_VIEW_TYPE,"Manchester City"))
        listTopPlayers.add(PlayerData(PlayersAdapter.PLAYER_VIEW_TYPE,"Kevin De Bruyne"))
        listTopPlayers.add(PlayerData(PlayersAdapter.PLAYER_VIEW_TYPE,"Sergio Aguero"))
        listTopPlayers.add(PlayerData(PlayersAdapter.CLUB_VIEW_TYPE,"Paris Saint-Germain"))
        listTopPlayers.add(PlayerData(PlayersAdapter.PLAYER_VIEW_TYPE,"Neymar Jr."))
        listTopPlayers.add(PlayerData(PlayersAdapter.PLAYER_VIEW_TYPE,"Kylian Mbappé"))
        listTopPlayers.add(PlayerData(PlayersAdapter.CLUB_VIEW_TYPE,"Liverpool"))
        listTopPlayers.add(PlayerData(PlayersAdapter.PLAYER_VIEW_TYPE,"Virgil van Dijk"))
        listTopPlayers.add(PlayerData(PlayersAdapter.PLAYER_VIEW_TYPE,"Mohamed Salah"))
        listTopPlayers.add(PlayerData(PlayersAdapter.PLAYER_VIEW_TYPE,"Sadio Mané"))


        playersAdapter = PlayersAdapter( this, listTopPlayers )

        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = playersAdapter
        }


    }

}