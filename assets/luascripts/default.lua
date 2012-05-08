-- Script : Cue
---- Système de files simple


cue = {}

function cue.new()
	local self = {}

	function self:push(data)
		self[#self+1]=data
		--fluxus:push("pushed "..data)
	end

	function self:getTruthTable()
		local tab = {}
		for i=0,77 do
			tab[i]=false
			--fluxus:push("init de la case "..i)
		end
		for i=1,#self do
			--fluxus:push("J'ai le "..tarot.utile.getNom(i))
			tab[self[i]]=true
		end
		return tab
	end
			
	
	function self:pop(index)
		index = index or 1
		if #self >= index then
			local data = self[index]
			table.remove(self, index)
			return data
		end
	end

	function self:isEmpty()
		return #self == 0
	end

	function self:clear()
		while not self:isEmpty() do
			self:pop()
		end
	end

	return self
end



-- Script : Gestion du flux de sortie
---- Crée un tableau de chaines de charatères et des fonctions associées


fluxus = cue.new()
	
fluxus:push('Fluxus initialisé')			



-- Script : Initialisation du namespace 'tarot'
---- Crée une table 'tarot' qui sera utilisée par les différentes fonctions de l'IA


tarot={}
tarot.main=cue.new()
tarot.legal=cue.new()
tarot.pli=cue.new()
tarot.utile = {}
fluxus:push('Namespaces créés')

function tarot.utile.getCouleur(carte)
	if carte < 0 or carte >=78 then
		return "erreur"
	elseif carte < 22 then
		return "atout"
	elseif carte < 36 then
		return "coeur"
	elseif carte < 50 then
		return "pique"
	elseif carte < 64 then
		return "carreau"
	else
		return "trefle"
	end
end

function tarot.utile.possede(tab, carte, ...)
	if carte==nil then
		return false
	else
		for i,v in ipairs(tab) do
			if carte == v then
				return carte
			end
		end
		return tarot.utile.possede(tab, ...)
	end
end

function tarot.utile.getValeur(carte)
	local couleur = tarot.utile.getCouleur(carte)
	if couleur == "atout" then
		return carte
	else
		local decalage = 0
		if couleur == "coeur" then
			decalage = 21
		elseif couleur == "pique" then
			decalage = 35
		elseif couleur == "carreau" then
			decalage = 49
		else
			decalage = 63
		end
		return carte - decalage
	end
end

function tarot.utile.newCarte(couleur, valeur)
	local couleur = couleur or "atout"
	local valeur = valeur or 1
	if couleur == "atout" then
		return valeur
	else
		local decalage = 0
		if couleur == "coeur" then
			decalage = 21
		elseif couleur == "pique" then
			decalage = 35
		elseif couleur == "carreau" then
			decalage = 49
		else
			decalage = 63
		end
		return decalage + valeur
	end
end

function tarot.utile.getNom(carte)
	if tarot.utile.getCouleur(carte)=="atout" then
		if carte == 0 then
			return "Excuse"
		elseif carte == 1 then
			return "Petit"
		else
			return carte.." d'atout"
		end
	else
		if tarot.utile.getValeur(carte)<11 then
			return tarot.utile.getValeur(carte).." de "..tarot.utile.getCouleur()
		else
			if tarot.utile.getValeur(carte)==11 then
				return "Valet de "..tarot.utile.getCouleur()
			elseif tarot.utile.getValeur(carte)==12 then
				return "Cavalier de "..tarot.utile.getCouleur()
			elseif tarot.utile.getValeur(carte)==13 then
				return "Dame de "..tarot.utile.getCouleur()
			else
				return "Roi de "..tarot.utile.getCouleur()
			end
		end
	end
end

-- Script : Carte aléatoire
---- 


function tarot.rndCard()
	return math.random(0,77)
end
flal = 5



-- Script : Tables standard
---- Utilisés par l'IA par défaut et utilisables par des IA customisés


tarot.chien=cue.new()
tarot.joueur={}
for i=1,4 do
	tarot.joueur[i]={}
	tarot.joueur[i].plis=cue.new()
	tarot.joueur[i].cartes={}
	tarot.joueur[i].cartes.possible={}
	tarot.joueur[i].cartes.probable={}
	for c=0,77 do
		tarot.joueur[i].cartes.possible[i]=true
		tarot.joueur[i].cartes.probable[i]=50
	end
end					

-- Script : Operations Callback
---- Fonctions à redéfinir pour créer une IA


tarot.demander = {}
tarot.dire = {}
	
function tarot.demander.annonce()
	return math.random(0,4)
end
	
function tarot.demander.ecart()
	local ecart = cue.new()
	for i=1,6 do
		ecart:push(tarot.main:pop(math.random(1,#tarot.main)))
	end
	return ecart
end

lastplayed = 100

function tarot.demander.carte()
	fluxus:push("J'ai "..#tarot.main.." cartes dans la main, dont "..#tarot.legal.." légales")
	--c = tarot.legal:pop(math.random(1,#tarot.legal))
	if #tarot.legal == 0 then
		fluxus:push("OMFG, I'm illegal!")
		c = math.random(0,77)
	else
		c = tarot.legal:pop(math.random(1,#tarot.legal))
		fluxus:push("Je veux jouer: "..tarot.utile.getNom(c))
	end
	if lastplayed == c then
		fluxus:push("J'ai déjà joué "..tarot.utile.getNom(c).." a la place je joue le 21")
		c = 21
		lastplayed = 21
	else
		lastplayed = c
	end
	return c
end
	
function tarot.dire.chien(chien)
end
	
function tarot.dire.cartejouee(carte, joueur)
end
	
function tarot.dire.annonce(annonce, joueur)
end
	
function tarot.dire.pliRemporte(pli, joueur)
end
	
function tarot.dire.main(main)
end
	
function tarot.dire.score(score)
end


-- Script : Script de test Lua
---- Script sans réelle utilité, ne sert que pour vérifier que Lua est chargé.


fluxus:push('Script de test chargé')



-- Script : annonces
----  Permet de renvoyer l'annonce que veux faire l'IA (indexer 0 à 4)


flal = 1
function tarot.demander.annonce()
	local c = 0	
	local j = 21
	local tempC = 0
	nbrCarteParCouleur = {}
	nbrAtout = 0
	nbrAtoutMajeur =0
	
	maMain = tarot.main:getTruthTable()
	
	for i=0,21 do
		if maMain[i] then 
			nbrAtout = nbrAtout + 1
		end
	end
	
	for i=16,21 do
		if maMain[i] then
			nbrAtoutMajeur = nbrAtoutMajeur + 1
		end
	end

	c=(c+(nbrAtoutMajeur*2))

	for i=17,21 do
		if maMain[i-1] and maMain[i] then
			c=(c+1)
		end
	end
				
	if maMain[21] then
		c=(c+10)
	end
	if maMain[0] then
		c=(c+8)
	end
	
	if maMain[1] then
		if 5 > nbrAtout then
		end
		if nbrAtout == 4 then
			c=(c+5)
		end
		if nbrAtout == 5 then
			c=(c+7)
		end
		if nbrAtout >= 6 then
			c=(c+9)
		end
			
		if nbrAtout > 4 then
			c=(c+(nbrAtout*2))
		end				
	end
	for i=1,4 do
		nbrCarteParCouleur[i] = 0
		if maMain[j+14] and maMain[j + 13] then 
			c=(c+1)
		end
		if maMain[j+14] then 
			c=(c+6)
		end
		if maMain[j + 13] then 
			c=(c+3)
		end
		if maMain[j + 12] then 
			c=(c+2)
		end
		if maMain[j + 11] then 
			c=(c+1)
		end
		
		for k=1,14 do
			if maMain[j+k] then 
				nbrCarteParCouleur[i] = nbrCarteParCouleur[i]+1
			end
		end
		
		if nbrCarteParCouleur[i] == 0 then 
			c=(c+6)
		elseif nbrCarteParCouleur[i] == 1 then 
			c=(c+3)
		elseif nbrCarteParCouleur[i] == 5 then 
			c=(c+5)
		elseif nbrCarteParCouleur[i] == 6 then 
			c=(c+7)
		elseif nbrCarteParCouleur[i] >= 7 then 
			c=(c+9)
		end
		
		j = j + 14
	end
	flal=c
	fluxus:push("Mon flal est "..flal)
	if c > 80 then 
		fluxus:push("Je garde contre")
		return 4, flal
	elseif c > 70 then
		fluxus:push("Je garde sans") 
		return 3, flal
	elseif c > 55 then
		fluxus:push("Je garde")
		return 2, flal
	elseif c > 40 then 
		fluxus:push("Je petite")
		return 1, flal
	else
		fluxus:push("Je passe")
		return 0, flal
	end
end	


function tarot.demander.ecart()
	local ecart = cue.new()
	CR = 6 -- nbr de Carte Restante a mettre au chien 
	fluxus:push("Entré dans tarot.demander.ecart()")
	maMain = tarot.main:getTruthTable() -- récuperation de la main
	--[[for i,v in ipairs(maMain) do
		if v then
			fluxus:push("J'ai le "..tarot.utile.getNom(i))
		else
			fluxus:push("J'ai n'ai pas le "..tarot.utile.getNom(i))
		end
	end]]
	while CR ~= 0 do
		fluxus:push("Je fais mon écart")
		--local cand = tarot.main:pop(1,#tarot.main)
		--fluxus:push("Candidat "..cand)
		--fluxus:push("soit "..tarot.utile.getNom(cand))
		--[[if tarot.utile.getValeur(cand)<11 then
			--fluxus:push("Je veux ecarter le "..tarot.utile.getNom(cand))
			if maMain[cand] then
				ecart:push(cand)
				CR = CR - 1
				--fluxus:push(tarot.utile.getNom(cand).." est bon pour l'ecart")
			end
		end]]
		local rand = math.random(0,3)
		local l = rand*14
		for j=1,10 do
			--fluxus:push("Je vais jeter des cartes de couleur "..l)
			if maMain[l+j+21] and CR~=0 then
				fluxus:push("Je mets "..(l+j+21).." à l'écart")
				ecart:push(l+j+21)
				CR = CR - 1
				maMain[l+j+21] = false
			end
		end
	end
	return ecart
end

function tarot.demander.carte()
	table.sort(tarot.legal)
	local pos = #tarot.pli%4
	
	for i,v in ipairs(tarot.main) do
		print("Flolilol"..v)
	end
	-- Avant dernier tour et j'ai toujours l'excuse, je le BALANCE
	fluxus:push("J'ai "..#tarot.main.." cartes dans la main, dont "..#tarot.legal.." légales")
	--fluxus:push("Il reste "..#tarot.main.." cartes dans ma main")
	if #tarot.main == 2 and tarot.utile.possede(tarot.legal, 0) then
		fluxus:push("Je balance l'excuse!")
		return 0
	end
	-- J'ouvre le pli
	if pos == 0 then
		fluxus:push("Je joue la derniere carte légale (parce que je peux)")
		return tarot.legal[#tarot.legal]
	end

	-- Quelqu'un joue après moi
	if pos == 1 or pos == 2 then


		-- Jeter une merde mais pas l'excuse
		if tarot.utile.possede(tarot.legal, 0) then
			fluxus:push("J'ai l'excuse, je joue une merde")
			return tarot.legal[2]
		else
			fluxus:push("Je joue une merde")
			return tarot.legal[1]
		end
	end

	-- Je termine ce pli, faudrait que je place des points s'ils passent, ou que je tente de remporter le pli si il y a des points dedans et que c'est pas ma team qui a la main, ou que ma team a la main, mais c'est mieux si j'ai la main. Sinon, je tej' de la merde
	if pos == 3 then
		-- Si je peux sauver mon petit, je le sauve
		if tarot.utile.possede(tarot.legal, 1) and not tarot.utile.possede(tarot.pli, 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21) then
			fluxus:push("Je sauve le petit")
			return 1
		end

		-- Jeter une merde mais pas l'excuse ni le petit
		local d = 1
		if tarot.utile.possede(tarot.legal, 1) then
			fluxus:push("J'ai le petit")
			d = d+1
		end
		if tarot.utile.possede(tarot.legal, 0) then
			fluxus:push("J'ai l'excuse")
			d = d+1
		end
		fluxus:push("Je joue ma plus petite carte légale")
		return tarot.legal[d] or tarot.legal[1]
	end

	fluxus:push("Euh... il y a "..#tarot.pli.." cartes dans le pli")
	return tarot.legal[#tarot.legal]

	-- Comportements "spéciaux"
		-- Jeu du petit

		-- Jeu de l'excuse DONE

		-- Chasse au petit

		-- Ambulance

	-- Identifier les cartes à "proteger" c'est à dire jouer à un moment avec une forte probabilité de garder la carte

		-- Si une carte à proteger a une forte chance de passer, jouer cette carte
	
	-- Si premier du pli
		-- Tenter une ouverture si avant le preneur

		-- Identifier les coupes des joueurs autour de la table
	
			-- Jouer une carte sur une coupe adverse

		-- Jouer une carte basse et sans valeur, à la couleur la plus longue


end

scriptloaded = true
