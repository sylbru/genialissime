-- Script : Cue
---- Système de files simple


cue = {}

function cue.new()
	local self = {}

	function self:push(data)
		self[#self+1]=data
	end

	function self:getTruthTable()
		local tab = {}
		for i=0,77 do
			tab[i]=false
		end
		for i=1,#self do
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

function tarot.utile.getValeur(carte)
	local couleur = tarot.getCouleur(carte)
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
		ecart:push(tarot.main:pop(math.random(0,#tarot.main)))
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
	end
	fluxus:push("Je veux jouer la carte numéro "..tarot.utile.getNom(c))
	if lastplayed == c then
		fluxus:push("Oh my god, I already played "..tarot.utile.getNom(c).." I'm going to cheat and play 21")
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

scriptloaded = true


