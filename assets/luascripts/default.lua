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
fluxus:push('Namespaces créés')



-- Script : Carte aléatoire
---- 


function tarot.rndCard()
	return math.random(0,77)
end
flal = 5



-- Script : Fonctions sur les cartes
---- 


function tarot.main.rndGetCard()
	return tarot.main[math.random(1, #tarot.main)]
end
function tarot.main.rndPopCard()
	return tarot.main:pop(math.random(#tarot.main))
end
	
fluxus:push('Fonctions sur la main créés')



-- Script : Contrats
---- WOOHOO


function tarot.rndContrat()
	return math.random(0,4)
end 




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
	
function tarot.demander.annonce2()
	return math.random(0,4)
end
	
function tarot.demander.ecart()
	local ecart = cue.new()
	for i=1,6 do
		ecart:push(tarot.main:pop(math.random(0,#tarot.main)))
	end
	return ecart
end
	
function tarot.demander.carte()
	return tarot.legal:pop(math.random(0,#tarot.legal))
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


