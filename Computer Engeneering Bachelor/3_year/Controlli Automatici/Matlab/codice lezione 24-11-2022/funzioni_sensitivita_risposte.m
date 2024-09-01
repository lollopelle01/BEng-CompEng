% Esempi funzioni di sensitività e risposte del sistema
% 
% Controlli Automatici T
% 2022/23
%

clear all, close all, clc


s = tf('s');


%% ESEMPIO FUNZIONI DI SENSITIVITÀ

% definizione sistema in anello aperto
mu = 40;
TT = 10;
TT2 = 2;
TT3 = 0.2;

RR = mu;
GG = 1/(1 + TT*s)/(1 + TT2*s)/(1 + TT3*s);
LL = mu * GG;
LL.Name = 'Sistema in anello aperto';
LL.OutputName = 'y';
LL.InputName  = 'u';

% margini di stabilità
[M_a,M_f,omega_pi,omega_c] = margin(LL)
M_a_db = 20*log10(M_a)

% funzione di sensitività complementare
FF = LL / (1 + LL);
FF.OutputName = 'y';
FF.InputName  = 'w';

% funzione di sensitività
SS = 1 / (1 + LL);
SS.OutputName = 'y';
SS.InputName  = 'd';


% bode plot
figure(1)
bode(LL)
grid on, zoom on


figure(2)
bode(FF)
grid on, zoom on


figure(3)
bode(SS)
grid on, zoom on


%% ESEMPIO RISPOSTE ANELLO APERTO E CHIUSO 
%  Disturbo in uscita

omega_w = 0.01;
omega_d = 0.05;

tt=(0:1e-2:2e3)'; % 2000 secondi con passo 0.01

% segnali in ingresso: riferimento e disturbo di misura
WW = 1;
DD = 1;

ww = WW*cos(omega_w*tt);
dd = DD*cos(omega_d*tt);


% Risposte in anello APERTO
y_w_OPEN = lsim(GG,ww,tt);
y_tot_OPEN = y_w_OPEN + dd;

% Risposte in anello CHIUSO
y_w = lsim(FF,ww,tt);
y_d = lsim(SS,dd,tt);
y_tot = y_w + y_d;

%% RISPOSTE IN ANELLO APERTO

figure()
hold on, grid on, zoom on
plot(tt,ww,'m')
plot(tt,y_w_OPEN,'b')
grid on
legend('ww','y_{w_{OPEN}}')


figure()
hold on, grid on, zoom on
plot(tt,ww,'m')
plot(tt,y_tot_OPEN,'b')
grid on
legend('ww','y_{tot_{OPEN}}')

%% RISPOSTE IN ANELLO CHIUSO

figure()
hold on, grid on, zoom on
plot(tt,ww,'m')
plot(tt,y_w,'b')
grid on
legend('ww','y_w')


figure()
hold on, grid on, zoom on
plot(tt,dd,'m')
plot(tt,y_d,'b')
grid on
legend('dd','y_d')


figure()
hold on, grid on, zoom on
plot(tt,ww,'m')
plot(tt,y_tot,'b')
grid on
legend('ww','y_{tot}')

